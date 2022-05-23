package pl.edu.agh.pwch.shop.payment.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import pl.edu.agh.pwch.shop.payment.service.PaymentService
import java.util.*
import javax.persistence.Embeddable

@Controller("payment")
class PaymentController {
    @Autowired
    lateinit var paymentService: PaymentService

    @PostMapping("charge")
    fun charge(chargeRequest: ChargeRequest): ChargeResponse =
        ChargeResponse(paymentService.createCharge(chargeRequest))
}

data class ChargeRequest(
    val amount: Money,
    val creditCard: CreditCardInfo,
)

data class ChargeResponse(
    val transactionId: UUID,
)

@Embeddable
data class Money(
    val currencyCode: String,
    val units: Int,
    val nanos: Int,
)

@Embeddable
data class CreditCardInfo(
    val cardNumber: String,
    val cardCVV: Int,
    val cardExpirationYear: Int,
    val cardExpirationMonth: Int,
)