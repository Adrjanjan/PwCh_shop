package pl.edu.agh.pwch.shop.basket.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import pl.edu.agh.pwch.shop.basket.controller.AddItemRequest
import pl.edu.agh.pwch.shop.basket.controller.BasketDto
import pl.edu.agh.pwch.shop.basket.controller.DeleteItemRequest
import pl.edu.agh.pwch.shop.basket.controller.ItemDto
import pl.edu.agh.pwch.shop.basket.model.Basket
import pl.edu.agh.pwch.shop.basket.repository.BasketRepository
import java.util.*

@Service
class BasketService {

    @Autowired
    lateinit var basketRepository: BasketRepository

    fun addItem(itemDto: AddItemRequest) = basketRepository.findById(itemDto.userId)
        .ifPresentOrElse(
            {
                it!!.items.replace(itemDto.item.productId, itemDto.item.quantity)
                basketRepository.save(it)
            },
            {
                basketRepository.save(
                    Basket(
                        itemDto.userId,
                        mutableMapOf(itemDto.item.productId to itemDto.item.quantity)
                    )
                )
            }
        )

    fun getBasket(userId: UUID): BasketDto? =
        basketRepository.findById(userId).orElseGet { null }
            ?.let { BasketDto(it.userId, it.items.map { item -> ItemDto(item.key, item.value) }) }

    fun deleteItem(deleteItemRequest: DeleteItemRequest) = basketRepository.findById(deleteItemRequest.userId)
        .ifPresent {
            it.items.remove(deleteItemRequest.item.productId)
            basketRepository.save(it)
        }

    fun delete(userId: UUID) = basketRepository.deleteById(userId)
}
