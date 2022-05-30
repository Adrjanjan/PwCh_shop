package pl.edu.agh.pwch.shop.currency.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import pl.edu.agh.pwch.shop.currency.service.CurrencyService
import pl.edu.agh.pwch.shop.shareddto.currency.Currency
import pl.edu.agh.pwch.shop.shareddto.payment.Money

@RestController
@RequestMapping("/currency")
class CurrencyController {

    @Autowired
    lateinit var currencyService: CurrencyService

    @GetMapping
    fun getSupportedCurrencies() = currencyService.getSupportedCurrencies()

    @PostMapping("/convert")
    fun convert(@RequestBody currencyConversionRequest: CurrencyConversionRequest) =
        currencyService.convert(currencyConversionRequest.from, currencyConversionRequest.toCurrency)

}

data class CurrencyConversionRequest(
    val from: Money,
    val toCurrency: Currency
)
