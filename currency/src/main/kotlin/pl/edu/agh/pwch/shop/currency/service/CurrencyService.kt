package pl.edu.agh.pwch.shop.currency.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import pl.edu.agh.pwch.shop.shareddto.currency.Currency
import pl.edu.agh.pwch.shop.shareddto.payment.Money
import kotlin.math.floor
import kotlin.math.pow
import kotlin.math.round

@Service
class CurrencyService {
    @Autowired
    lateinit var ratesService: RatesService

    fun getSupportedCurrencies() = listOf(Currency.EUR, Currency.USD, Currency.PLN)

    fun convert(from: Money, toCode: Currency): Money {
        val inEuros = carry(
            from.units.toDouble() / ratesService.conversionRateToEurForCode(from.currencyCode),
            from.nanos.toDouble() / ratesService.conversionRateToEurForCode(from.currencyCode)
        )

        var result = inEuros.first to round(inEuros.second)
        if (toCode != Currency.EUR) {
            result = carry(
                inEuros.first * ratesService.conversionRateToEurForCode(toCode),
                inEuros.second * ratesService.conversionRateToEurForCode(toCode)
            )
        }
        return Money(toCode, floor(result.first).toInt(), floor(result.second).toLong())
    }

    fun carry(units: Double, nanos: Double): Pair<Double, Double> {
        val fractionSize = (10.0).pow(9)
        var n = nanos + units.mod(1.0) * fractionSize
        val u = floor(units) + floor(n / fractionSize)
        n = floor(n.mod(fractionSize))
        return u to n
    }

}
