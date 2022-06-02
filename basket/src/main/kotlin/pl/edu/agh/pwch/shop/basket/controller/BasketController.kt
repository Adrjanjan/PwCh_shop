package pl.edu.agh.pwch.shop.basket.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import pl.edu.agh.pwch.shop.basket.service.BasketService
import pl.edu.agh.pwch.shop.shareddto.basket.BasketDto
import pl.edu.agh.pwch.shop.shareddto.basket.ItemDto
import java.util.*

@RestController
@RequestMapping("/basket")
class BasketController {

    @Autowired
    lateinit var basketService: BasketService

    @GetMapping("/{userId}")
    fun getBasket(@PathVariable userId: UUID): BasketDto? = basketService.getBasket(userId)

    @PostMapping
    fun addItem(@RequestBody itemDto: AddItemRequest) {
        basketService.addItem(itemDto)
    }

    @DeleteMapping
    fun deleteItem(@RequestBody itemDto: DeleteItemRequest) = basketService.deleteItem(itemDto)

    @DeleteMapping("/{userId}")
    fun emptyBasket(@PathVariable userId: UUID) =basketService.delete(userId)

}

data class AddItemRequest(
    val userId: UUID,
    val item: ItemDto
)

data class DeleteItemRequest(
    val userId: UUID,
    val item: ItemDto
)
