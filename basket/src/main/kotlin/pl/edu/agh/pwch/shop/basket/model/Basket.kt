package pl.edu.agh.pwch.shop.basket.model

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import java.util.*

@RedisHash("basket", timeToLive = 172800) // 48h
class Basket(
    @Id
    val userId: UUID,
    val items: MutableMap<UUID, Int> = mutableMapOf()
)