package pl.edu.agh.pwch.shop.basket.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import pl.edu.agh.pwch.shop.basket.model.Basket
import java.util.*

@Repository
interface BasketRepository : CrudRepository<Basket?, UUID>