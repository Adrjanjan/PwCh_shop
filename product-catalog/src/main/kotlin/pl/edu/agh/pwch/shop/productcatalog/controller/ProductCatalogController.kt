package pl.edu.agh.pwch.shop.productcatalog.controller

import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
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
    fun listProducts(): List<Product> {
        LOGGER.info("[list Products] get all products")
        return productsRepository.findAll()
    }

    @GetMapping("/{productId}")
    fun getProduct(@PathVariable productId: UUID): Product? {
        LOGGER.info("[get Product] get product with id $productId")
        return productsRepository.findById(productId).get()
    }

    @GetMapping("/{productId}/price")
    fun getProductPrice(@PathVariable productId: UUID): Money {
        LOGGER.info("[get Product Price] get price of product with Id $productId")
        return productsRepository.findById(productId).get().priceUsd
    }

    companion object {
        private val LOGGER: Log = LogFactory.getLog(ProductCatalogController::class.java)
    }
}

