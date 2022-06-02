package pl.edu.agh.pwch.shop.order.service

import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestTemplate
import pl.edu.agh.pwch.shop.order.model.ShopOrder
import pl.edu.agh.pwch.shop.order.model.User
import pl.edu.agh.pwch.shop.order.repository.OrderRepository
import pl.edu.agh.pwch.shop.order.repository.UserRepository
import pl.edu.agh.pwch.shop.shareddto.basket.BasketDto
import pl.edu.agh.pwch.shop.shareddto.basket.ItemDto
import pl.edu.agh.pwch.shop.shareddto.currency.Currency
import pl.edu.agh.pwch.shop.shareddto.notification.EmailDto
import pl.edu.agh.pwch.shop.shareddto.order.*
import pl.edu.agh.pwch.shop.shareddto.payment.CreditCardInfo
import pl.edu.agh.pwch.shop.shareddto.payment.Money
import java.util.*

@Service
class OrderService {

    @Autowired
    lateinit var orderRepository: OrderRepository

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var restTemplate: RestTemplate

    @Autowired
    lateinit var pubSubEmailGateway: NotificationSender.PubSubEmailGateway

    @Autowired
    lateinit var conn: Connections

    companion object {
        private val LOGGER: Log = LogFactory.getLog(OrderService::class.java)
    }

    fun placeOrder(placeOrderRequest: PlaceOrderRequest): OrderResult {
        if (!userRepository.existsById(placeOrderRequest.userId)) {
            userRepository.save(
                User(
                    placeOrderRequest.userId,
                    placeOrderRequest.userCurrency,
                    placeOrderRequest.address,
                    placeOrderRequest.email,
                    placeOrderRequest.creditCard
                )
            )
        }
        LOGGER.info("[PlaceOrder] user: ${placeOrderRequest.userId} currency: ${placeOrderRequest.userCurrency}")

        val orderPreparation = prepareOrderItemsAndShippingQuoteFromBasket(
            placeOrderRequest.userId,
            placeOrderRequest.userCurrency,
            placeOrderRequest.address
        )
        var total = Money(placeOrderRequest.userCurrency, 0, 0)
        orderPreparation.orderItems.forEach {
            total += it.costMoney * it.basketItem.quantity
        }

        val transactionId = chargeCard(total, placeOrderRequest.creditCard)
        LOGGER.info("payment went through (transactionId: $transactionId)")
        val shippingTrackingId = shipOrder(
            placeOrderRequest.userId,
            placeOrderRequest.address,
            orderPreparation.orderItems.map { it.basketItem })!!
        emptyUserBasket(placeOrderRequest.userId)

        val shopOrder = ShopOrder(
            UUID.randomUUID(),
            User(
                placeOrderRequest.userId,
                placeOrderRequest.userCurrency,
                placeOrderRequest.address,
                placeOrderRequest.email,
                placeOrderRequest.creditCard
            ),
            total,
            shippingTrackingId
        )
        sendOrderConfirmation(placeOrderRequest.email, shopOrder)
        orderRepository.save(shopOrder)
        return OrderResult(
            shopOrder.id,
            shippingTrackingId,
            orderPreparation.shippingCostLocalized,
            placeOrderRequest.address,
            orderPreparation.orderItems,
        )
    }

    data class OrderPrep(
        val orderItems: List<OrderItem>,
        val shippingCostLocalized: Money,
    )


    fun prepareOrderItemsAndShippingQuoteFromBasket(userID: UUID, userCurrency: Currency, address: Address): OrderPrep {
        val basketItems = getUserBasket(userID)!!
        val orderItems = prepOrderItems(basketItems, userCurrency)
        val shippingUSD = quoteShipping(address, basketItems)!!
        val shippingPrice = convertCurrency(shippingUSD, userCurrency)!!
        return OrderPrep(orderItems, shippingPrice)
    }

    fun quoteShipping(address: Address, items: List<ItemDto>) = try { // GIT
        restTemplate.postForObject(
            "${conn.SHIPPING_ADDRESS}/shipping/quote",
            object {
                val address = address
                val items = items
            },
            Money::class.java
        )
    } catch (e: RestClientException) {
        LOGGER.error("[Quote Shipping] Can't get quote for address: $address")
        throw e
    }

    fun getUserBasket(userId: UUID) = try { // GIT
        restTemplate.getForObject("${conn.BASKET_ADDRESS}/basket/${userId}", BasketDto::class.java)?.items
    } catch (e: RestClientException) {
        LOGGER.error("[User Basket] Can't get basket for user $userId")
        throw e
    }

    fun emptyUserBasket(userId: UUID) { // GIT
        try {
            restTemplate.delete("${conn.BASKET_ADDRESS}/basket/$userId")
        } catch (e: RestClientException) {
            LOGGER.error("[Empty Basket] Can't empty basket for user $userId")
            throw e
        }
    }

    fun prepOrderItems(items: List<ItemDto>, userCurrency: Currency) = items.map {
        val productPriceUsd = getProductPrice(it.productId)!!
        val price = convertCurrency(productPriceUsd, userCurrency)!!
        OrderItem(it, price)
    }


    private fun getProductPrice(productId: UUID) = try { // GIT
        restTemplate.getForObject("${conn.CATALOG_ADDRESS}/products/$productId/price", Money::class.java)
    } catch (e: RestClientException) {
        LOGGER.error("[Get Product Price] Can't get price for product $productId")
        throw e
    }

    fun convertCurrency(from: Money, toCurrency: Currency) = try { //  GIT
        restTemplate.postForObject(
            "${conn.CURRENCY_ADDRESS}/currency/convert",
            object {
                val from = from
                val toCurrency = toCurrency
            },
            Money::class.java
        )
    } catch (e: RestClientException) {
        LOGGER.error("[Convert Currency] Can't convert currency")
        throw e
    }

    fun chargeCard(amount: Money, paymentInfo: CreditCardInfo) = try { // GIT
        restTemplate.postForObject(
            "${conn.PAYMENT_ADDRESS}/payment/charge",
            object {
                val amount = amount
                val paymentInfo = paymentInfo
            },
            UUID::class.java
        )
    } catch (e: RestClientException) {
        LOGGER.error("[Charge Card] Can't charge card ${paymentInfo.cardNumber}")
        throw e
    }

    fun sendOrderConfirmation(email: String, shopOrder: ShopOrder) = try {
        pubSubEmailGateway.sendEmailToPubSub(
            EmailDto(
                "sho@pwch.agh.edu.pl",
                email,
                "Your order ${shopOrder.id} is confirmed!",
                "Order ${shopOrder.id} confirmed!"
            )
        )
    } catch (e: RestClientException) {
        LOGGER.error("[Send Order Confirmation] Can't send order confirmation")
        throw e
    }

    fun shipOrder(orderId: UUID, address: Address, items: List<ItemDto>) = try { // GIT
        restTemplate.postForObject(
            "${conn.SHIPPING_ADDRESS}/shipping",
            object {
                val orderId = orderId
                val address = address
                val items = items
            },
            UUID::class.java
        )
    } catch (e: RestClientException) {
        LOGGER.error("[Ship Order] Can't ship order $orderId")
        throw e
    }

}
