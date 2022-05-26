package pl.edu.agh.pwch.shop.shareddto.payment

import pl.edu.agh.pwch.shop.shareddto.currency.Currency
import javax.persistence.Embeddable
import kotlin.math.floor
import kotlin.math.pow

@Embeddable
data class Money(
    val currencyCode: Currency,
    val units: Int,
    val nanos: Long,
) {
    operator fun plus(other: Money): Money {
        // different currencies should be handled, but we dont care
        val (units, nanos) = carry(units.toDouble() + other.units.toDouble(), nanos.toDouble() + other.nanos.toDouble())
        return Money(currencyCode, units.toInt(), nanos.toLong())
    }

    operator fun times(m: Int): Money {
        val (units, nanos) = carry(units.toDouble() * m, nanos.toDouble() * m)
        return Money(currencyCode, units.toInt(), nanos.toLong())
    }

    private fun carry(units: Double, nanos: Double): Pair<Double, Double> {
        val fractionSize = (10.0).pow(9)
        var n = nanos + (units - floor(units)) * fractionSize
        val u = floor(units) + floor(nanos / fractionSize)
        n = n.mod(fractionSize)
        return n to u
    }
}

@Embeddable
data class CreditCardInfo(
    val cardNumber: String,
    val cardCVV: Int,
    val cardExpirationYear: Int,
    val cardExpirationMonth: Int,
)