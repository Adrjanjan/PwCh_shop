package pl.edu.agh.pwch.shop.order.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import pl.edu.agh.pwch.shop.order.model.Order
import java.util.*

@Repository
interface OrderRepository : JpaRepository<Order, UUID>
