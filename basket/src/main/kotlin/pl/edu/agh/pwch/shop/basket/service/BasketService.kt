package pl.edu.agh.pwch.shop.basket.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import pl.edu.agh.pwch.shop.basket.controller.AddItemRequest
import pl.edu.agh.pwch.shop.basket.controller.DeleteItemRequest
import pl.edu.agh.pwch.shop.basket.model.Basket
import pl.edu.agh.pwch.shop.basket.repository.BasketRepository
import pl.edu.agh.pwch.shop.shareddto.basket.BasketDto
import pl.edu.agh.pwch.shop.shareddto.basket.ItemDto
import java.util.*

@Service
class BasketService {

    @Autowired
    lateinit var basketRepository: BasketRepository

    fun addItem(itemDto: AddItemRequest): Basket {
        val basket = basketRepository.findById(itemDto.userId)
        val productId = itemDto.item.productId
        return if (basket.isPresent) {
            if (basket.get().items[productId] == null) {
                basket.get().items[productId] = itemDto.item.quantity
            } else {
                basket.get().items.replace(productId, itemDto.item.quantity)
            }
            basketRepository.save(basket.get())
        } else {
            basketRepository.save(
                Basket(
                    itemDto.userId,
                    mutableMapOf(productId to itemDto.item.quantity)
                )
            )
        }
    }

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
