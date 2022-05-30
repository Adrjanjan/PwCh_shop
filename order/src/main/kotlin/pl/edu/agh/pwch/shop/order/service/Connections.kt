package pl.edu.agh.pwch.shop.order.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class Connections {
    @Value("\${shipping-service-address}")
    lateinit var SHIPPING_ADDRESS: String

    @Value("\${basket-service-address}")
    lateinit var BASKET_ADDRESS: String

    @Value("\${product-catalog-service-address}")
    lateinit var CATALOG_ADDRESS: String

    @Value("\${currency-service-address}")
    lateinit var CURRENCY_ADDRESS: String

    @Value("\${notification-service-address}")
    lateinit var NOTIFICATION_ADDRESS: String

    @Value("\${payment-service-address}")
    lateinit var PAYMENT_ADDRESS: String

}