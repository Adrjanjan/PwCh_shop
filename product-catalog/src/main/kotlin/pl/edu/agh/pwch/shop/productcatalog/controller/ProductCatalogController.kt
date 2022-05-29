package pl.edu.agh.pwch.shop.productcatalog.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import pl.edu.agh.pwch.shop.productcatalog.Product
import pl.edu.agh.pwch.shop.productcatalog.repository.ProductsRepository
import pl.edu.agh.pwch.shop.shareddto.payment.Money
import java.util.*

@RestController
@RequestMapping("/products")
class ProductCatalogController {

    @Autowired
    lateinit var productsRepository: ProductsRepository

    @GetMapping
    fun listProducts(): List<Product> = productsRepository.findAll()

    @GetMapping("/{productId}")
    fun getProduct(@PathVariable productId: UUID): Product? = productsRepository.findById(productId).get()

    @GetMapping("/{productId}/price")
    fun getProductPrice(@PathVariable productId: UUID): Money = productsRepository.findById(productId).get().priceUsd

    @GetMapping("/search")
    fun searchProducts(@RequestParam q: String) = productsRepository.findByNameLike(q)
}

