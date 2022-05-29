package pl.edu.agh.pwch.shop.productcatalog

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.core.io.Resource
import org.springframework.stereotype.Component
import pl.edu.agh.pwch.shop.productcatalog.repository.ProductsRepository


@Component
class MockDataGenerator {
    @Autowired
    lateinit var productRepository: ProductsRepository

    @Value("classpath:products.json")
    lateinit var mockProducts: Resource

    @EventListener
    fun insertMockProducts(event: ApplicationReadyEvent) {
        val mapper = ObjectMapper()
        val products = mapper.readValue(mockProducts.inputStream, object : TypeReference<List<Product>>() {})
        products.forEach { productRepository.save(it) }
    }
}