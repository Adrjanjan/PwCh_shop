package pl.edu.agh.pwch.shop.shipment.service

import org.springframework.stereotype.Service
import pl.edu.agh.pwch.shop.shareddto.basket.ItemDto
import pl.edu.agh.pwch.shop.shareddto.currency.Currency
import pl.edu.agh.pwch.shop.shareddto.order.Address
import pl.edu.agh.pwch.shop.shareddto.payment.Money
import java.util.*

@Service
class ShippingService {

    fun createQuoteFromCount(address: Address, items: List<ItemDto>): Money = getMockQuote()

    private fun getMockQuote() = Money(Currency.USD, 10, 99 * 1_000_000_000L)

    fun shipOrder(address1: UUID, address: Address, items: List<ItemDto>): UUID {
        //ship order
        return UUID.randomUUID()
    }

}
