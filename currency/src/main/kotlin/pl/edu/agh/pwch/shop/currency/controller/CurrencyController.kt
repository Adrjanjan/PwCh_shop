package pl.edu.agh.pwch.shop.currency.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import pl.edu.agh.pwch.shop.currency.model.Money
import pl.edu.agh.pwch.shop.currency.service.CurrencyService
import pl.edu.agh.pwch.shop.shareddto.currency.Currency

@RestController("/currency")
class CurrencyController {

    @Autowired
    lateinit var currencyService: CurrencyService

    @GetMapping
    fun getSupportedCurrencies() = currencyService.getSupportedCurrencies()

    @PostMapping("convert")
    fun convert(@RequestBody currencyConversionRequest: CurrencyConversionRequest) =
        currencyService.convert(currencyConversionRequest.from, currencyConversionRequest.toCurrency)

}

data class CurrencyConversionRequest(
    val from: Money,
    val toCurrency: Currency
)
