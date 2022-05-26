package pl.edu.agh.pwch.shop.shareddto.basket

import java.util.*


data class BasketDto(
    val userId: UUID,
    val items: List<ItemDto>
)

data class ItemDto(
    val productId: UUID,
    val quantity: Int
)
