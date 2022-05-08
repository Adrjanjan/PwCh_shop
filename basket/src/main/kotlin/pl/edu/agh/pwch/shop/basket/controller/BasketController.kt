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
    fun getItems(@RequestBody userId: UUID) = basketService.getBasket(userId)


    @PostMapping
    fun addItem(@RequestBody itemDto: ItemDto){
        basketService.addItem(itemDto)
    }

    @DeleteMapping
    fun deleteItem(@RequestBody itemDto: ItemDto) = basketService.deleteItem(itemDto)


}

data class ItemDto(
    val userId: UUID,
    val itemId: UUID,
    val itemQuantity: Int
)
