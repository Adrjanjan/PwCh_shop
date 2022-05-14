package pl.edu.agh.pwch.shop.basket.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import pl.edu.agh.pwch.shop.basket.service.BasketService
import java.util.*

@RestController("/basket")
class BasketController {

    @Autowired
    lateinit var basketService: BasketService

    @GetMapping
    fun getBasket(@RequestBody getBasketRequest: GetBasketRequest): BasketDto? =
        basketService.getBasket(getBasketRequest.userId)


    @PostMapping
    fun addItem(@RequestBody itemDto: AddItemRequest) {
        basketService.addItem(itemDto)
    }

    @DeleteMapping
    fun deleteItem(@RequestBody itemDto: DeleteItemRequest) = basketService.deleteItem(itemDto)

    @DeleteMapping("/all")
    fun emptyBasket(@RequestBody emptyBasketRequest: EmptyBasketRequest) =
        basketService.delete(emptyBasketRequest.userId)

}

data class BasketDto(
    val userId: UUID,
    val items: List<ItemDto>
)

data class ItemDto(
    val productId: UUID,
    val quantity: Int
)

data class AddItemRequest(
    val userId: UUID,
    val item: ItemDto
)
data class DeleteItemRequest(
    val userId: UUID,
    val item: ItemDto
)

data class EmptyBasketRequest(
    val userId: UUID
)

data class GetBasketRequest(
    val userId: UUID
)
