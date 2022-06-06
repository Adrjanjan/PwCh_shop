package pl.edu.agh.pwch.shop.basket.controller

import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
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
    fun getBasket(@PathVariable userId: UUID): BasketDto? {
        LOGGER.info("[Get Basket] for user $userId")
        return basketService.getBasket(userId)
    }

    @PostMapping
    fun addItem(@RequestBody itemDto: AddItemRequest) {
        LOGGER.info("[Add item] for user ${itemDto.userId}")
        basketService.addItem(itemDto)
    }

    @DeleteMapping
    fun deleteItem(@RequestBody itemDto: DeleteItemRequest) {
        LOGGER.info("[Delete Item] for user ${itemDto.userId}")
        return basketService.deleteItem(itemDto)
    }

    @DeleteMapping("/{userId}")
    fun emptyBasket(@PathVariable userId: UUID) {
        LOGGER.info("[Empty Basket] for user $userId")
        basketService.delete(userId)
    }

    companion object {
        private val LOGGER: Log = LogFactory.getLog(BasketController::class.java)
    }
}

data class AddItemRequest(
    val userId: UUID,
    val item: ItemDto
)

data class DeleteItemRequest(
    val userId: UUID,
    val item: ItemDto
)
