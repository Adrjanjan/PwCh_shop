package pl.edu.agh.pwch.shop.currency.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import pl.edu.agh.pwch.shop.currency.repository.CurrencyRepository
import pl.edu.agh.pwch.shop.currency.model.Currency
import pl.edu.agh.pwch.shop.currency.model.Money
import kotlin.math.floor
import kotlin.math.pow
import kotlin.math.round

@Service
class CurrencyService {
    @Autowired
    lateinit var currencyRepository : CurrencyRepository

    fun getSupportedCurrencies() = listOf(Currency.EUR, Currency.USD, Currency.PLN)

    fun convert(from: Money, toCode: Currency): Money {
        var inEuros = carry(
            from.units.toDouble() / currencyRepository.conversionRateToEurForCode(from.currencyCode),
            from.nanos.toDouble() / currencyRepository.conversionRateToEurForCode(from.currencyCode)
        )

        inEuros = inEuros.first to round(inEuros.second)

        val result = carry(
            inEuros.first * currencyRepository.conversionRateToEurForCode(toCode),
            inEuros.second * currencyRepository.conversionRateToEurForCode(toCode)
        )
        return Money(toCode, floor(result.first).toInt(), floor(result.second).toInt())
    }

    fun carry(units: Double, nanos: Double) : Pair<Double,Double> {
        val fractionSize = (10.0).pow(9)
        var n = nanos + (units - floor(units)) * fractionSize
        val u  = floor(units) + floor(nanos / fractionSize)
        n = n.mod(fractionSize)
        return n to u
    }

}
