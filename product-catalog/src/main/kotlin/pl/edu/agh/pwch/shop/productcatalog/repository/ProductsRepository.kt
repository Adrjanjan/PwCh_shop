package pl.edu.agh.pwch.shop.productcatalog.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import pl.edu.agh.pwch.shop.productcatalog.Product
import java.util.*

@Repository
interface ProductsRepository : JpaRepository<Product, UUID>