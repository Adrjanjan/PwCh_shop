package pl.edu.agh.pwch.shop.payment.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.edu.agh.pwch.shop.payment.service.PaymentService
import pl.edu.agh.pwch.shop.shareddto.payment.CreditCardInfo
import pl.edu.agh.pwch.shop.shareddto.payment.Money

@RestController
@RequestMapping("/payment")
class PaymentController {
    @Autowired
    lateinit var paymentService: PaymentService

    @PostMapping("charge")
    fun charge(chargeRequest: ChargeRequest) = paymentService.createCharge(chargeRequest)
}

data class ChargeRequest(
    val amount: Money,
    val paymentInfo: CreditCardInfo,
)