package pl.edu.agh.pwch.shop.order.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import pl.edu.agh.pwch.shop.order.service.OrderService
import pl.edu.agh.pwch.shop.shareddto.order.OrderResult
import pl.edu.agh.pwch.shop.shareddto.order.PlaceOrderRequest

@Controller
class OrderController {

    @Autowired
    lateinit var orderService: OrderService

    @PostMapping
    fun placeOrder(placeOrderRequest: PlaceOrderRequest): OrderResult = orderService.placeOrder(placeOrderRequest)
}

