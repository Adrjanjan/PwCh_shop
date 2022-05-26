package pl.edu.agh.pwch.shop.shareddto.order

import pl.edu.agh.pwch.shop.shareddto.basket.ItemDto
import pl.edu.agh.pwch.shop.shareddto.currency.Currency
import pl.edu.agh.pwch.shop.shareddto.payment.CreditCardInfo
import pl.edu.agh.pwch.shop.shareddto.payment.Money
import java.util.*
import javax.persistence.Embeddable

data class PlaceOrderRequest(
    val userId: UUID,
    val userCurrency: Currency,
    val address: Address,
    val email: String,
    val creditCard: CreditCardInfo,
)

data class OrderResult(
    val orderId: UUID,
    val shippingTrackingId: UUID,
    val shippingCost: Money,
    val shippingAddress: Address,
    val items: List<OrderItem>
)

@Embeddable
data class Address(
    val streetAddress: String,
    val city: String,
    val state: String,
    val country: String,
    val zipCode: Int,
)

data class OrderItem(
    val basketItem: ItemDto,
    val costMoney: Money,
)