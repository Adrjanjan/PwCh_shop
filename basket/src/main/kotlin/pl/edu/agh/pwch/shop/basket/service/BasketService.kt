package pl.edu.agh.pwch.shop.basket.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import pl.edu.agh.pwch.shop.basket.controller.ItemDto
import pl.edu.agh.pwch.shop.basket.model.Basket
import pl.edu.agh.pwch.shop.basket.repository.BasketRepository
import java.util.*

@Service
class BasketService {

    @Autowired
    lateinit var basketRepository: BasketRepository

    fun addItem(itemDto: ItemDto) = basketRepository.findById(itemDto.userId)
        .ifPresentOrElse(
            {
                it!!.items.replace(itemDto.itemId, itemDto.itemQuantity)
                basketRepository.save(it)
            },
            {
                basketRepository.save(Basket(itemDto.userId, mutableMapOf(itemDto.itemId to itemDto.itemQuantity)))
            }
        )

    fun getBasket(userId: UUID): Basket? = basketRepository.findById(userId).get()

    fun deleteItem(itemDto: ItemDto) = basketRepository.findById(itemDto.userId)
        .ifPresent {
                it.items.remove(itemDto.itemId)
                basketRepository.save(it)
            }
}
