package pl.edu.agh.pwch.shop.productcatalog

import pl.edu.agh.pwch.shop.shareddto.payment.Money
import java.util.*
import javax.persistence.Entity
import javax.persistence.Id

@Entity
class Product (
    @Id
    val id: UUID,
    val name: String,
    val description: String,
    val picture: String,
    val priceUsd: Money,
    val categories: List<String>,
)
