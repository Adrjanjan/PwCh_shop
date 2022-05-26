package pl.edu.agh.pwch.shop.currency.model

import pl.edu.agh.pwch.shop.shareddto.currency.Currency
import javax.persistence.Embeddable

@Embeddable
data class Money(
    val currencyCode: Currency,
    val units: Int,
    val nanos: Int
) {
    fun fromCode(code: Currency) = Money(code, this.units, this.nanos)
    fun fromNanos(nanos: Int) = Money(this.currencyCode, this.units, nanos)

}