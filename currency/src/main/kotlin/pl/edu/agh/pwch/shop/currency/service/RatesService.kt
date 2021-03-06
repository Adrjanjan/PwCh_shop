package pl.edu.agh.pwch.shop.currency.service

import org.springframework.stereotype.Service
import pl.edu.agh.pwch.shop.shareddto.currency.Currency

@Service
class RatesService {

    var rates = mapOf(Currency.EUR to 1.0, Currency.USD to 1.03, Currency.PLN to 4.68)

    fun conversionRateToEurForCode(toCode: Currency) : Double{
        if(rates.isEmpty()){
            initRates()
        }
        return rates[toCode]!!
    }

    private fun initRates() {
        TODO("Could be initialized using European Central Bank API")
    }

}