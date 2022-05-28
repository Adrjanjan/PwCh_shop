package pl.edu.agh.pwch.shop.productcatalog

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@EnableJpaRepositories
@SpringBootApplication
class ProductCatalogApplication

fun main(args: Array<String>) {
    runApplication<ProductCatalogApplication>(*args)
}
