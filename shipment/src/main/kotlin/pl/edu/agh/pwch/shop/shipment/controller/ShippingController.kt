package pl.edu.agh.pwch.shop.shipment.controller

import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import pl.edu.agh.pwch.shop.shareddto.basket.ItemDto
import pl.edu.agh.pwch.shop.shareddto.order.Address
import pl.edu.agh.pwch.shop.shareddto.payment.Money
import pl.edu.agh.pwch.shop.shipment.service.ShippingService
import java.util.*

@Controller("shipping")
class ShippingController {

    companion object {
        private val LOGGER: Log = LogFactory.getLog(ShippingController::class.java)
    }

    @Autowired
    lateinit var shippingService: ShippingService

    @GetMapping("/quote")
    fun getQuote(@RequestBody getQuoteRequest: GetQuoteRequest): Money {
        LOGGER.info("[GetQuote] received request")
        return shippingService.createQuoteFromCount(getQuoteRequest.address, getQuoteRequest.items)
    }

    @PostMapping
    fun shipOrder(@RequestBody shipOrderRequest: ShipOrderRequest): UUID {
        LOGGER.info("[ShipOrder] ship request for order ${shipOrderRequest.orderId}")
        return shippingService.shipOrder(shipOrderRequest.orderId, shipOrderRequest.address, shipOrderRequest.items)
    }
}

data class GetQuoteRequest(
    val address: Address,
    val items: List<ItemDto>
)

data class ShipOrderRequest(
    val orderId: UUID,
    val address: Address,
    val items: List<ItemDto>
)
