package pl.edu.agh.pwch.shop.payment.service

import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import pl.edu.agh.pwch.shop.payment.controller.ChargeRequest
import pl.edu.agh.pwch.shop.payment.controller.CreditCardInfo
import pl.edu.agh.pwch.shop.payment.repository.Transaction
import pl.edu.agh.pwch.shop.payment.repository.TransactionRepository
import java.time.ZonedDateTime
import java.util.*

@Service
class PaymentService {

    @Autowired
    lateinit var transactionRepository: TransactionRepository

    fun createCharge(chargeRequest: ChargeRequest): UUID {
        val card = chargeRequest.creditCard
        val amount = chargeRequest.amount
        validateCard(card)
        LOGGER.info(
            "Transaction processed: Take ${amount.currencyCode}${amount.units}.${amount.nanos} from card ending with ${
                card.cardNumber.substring(card.cardNumber.length - 4)
            }"
        )
        return transactionRepository.save(Transaction(UUID.randomUUID(), amount = amount, creditCardInfo = card)).id
    }

    private fun validateCard(creditCard: CreditCardInfo) {
        val now = ZonedDateTime.now()
        val currentMonth = now.month.value
        val currentYear = now.year
        if ((currentYear * 12 + currentMonth) > (creditCard.cardExpirationYear * 12 + creditCard.cardExpirationMonth)) {
            throw ExpiredCreditCard(
                creditCard.cardNumber,
                creditCard.cardExpirationMonth,
                creditCard.cardExpirationYear
            )
        }
    }

    companion object {
        private val LOGGER: Log = LogFactory.getLog(PaymentService::class.java)
    }
}


open class CreditCardException(message: String) : RuntimeException(message)

class ExpiredCreditCard(number: String, month: Int, year: Int) :
    CreditCardException("Your credit card (ending ${number.substring(number.length - 4)}) expired on ${month}/${year}")
