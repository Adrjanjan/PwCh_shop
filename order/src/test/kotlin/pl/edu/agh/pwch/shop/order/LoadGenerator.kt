package pl.edu.agh.pwch.shop.order

import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.FilterType
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import pl.edu.agh.pwch.shop.shareddto.basket.ItemDto
import pl.edu.agh.pwch.shop.shareddto.currency.Currency
import pl.edu.agh.pwch.shop.shareddto.order.Address
import pl.edu.agh.pwch.shop.shareddto.payment.CreditCardInfo
import reactor.core.publisher.Mono
import java.util.*
import kotlin.math.absoluteValue
import kotlin.random.Random


@Disabled
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ComponentScan(
    excludeFilters = [
        ComponentScan.Filter(type = FilterType.REGEX, pattern = ["pl\\.agh.*"]),
    ]
)
class LoadGenerator {

    @Autowired
    lateinit var webTestClient: WebTestClient

    val products = listOf(
        UUID.fromString("990d261c-6d0d-4ca2-969d-bb7d6e1b7fa4"),
        UUID.fromString("b8c0af51-ff53-4ae8-ac88-56b371dd3754"),
        UUID.fromString("ca73ad05-6a55-4f2b-b3e6-fcbca43d8fe8"),
        UUID.fromString("88c74480-3d6a-4709-a427-b729a5d26b50"),
        UUID.fromString("f7bb4df5-c1d3-43fb-b181-8f0cc72c8b8b"),
        UUID.fromString("e5d31e0a-8a6c-46a1-9d6d-7759aed32bcb"),
        UUID.fromString("be02b1b3-0420-456f-97cf-d632c6da7cba"),
        UUID.fromString("52c0700c-d15f-491e-9454-7a8e66450934"),
        UUID.fromString("f3d6a0a4-ca6c-414a-9936-d14a7ad4b7fa"),
    )

    @Test
    fun testLoad() {
        val userId = UUID.randomUUID()
        val basketItem = Mono.just(
            AddItemRequest(
                userId,
                ItemDto(
                    products[Random(0).nextInt().absoluteValue % products.size],
                    Random(0).nextInt().absoluteValue % 10
                )
            )
        )
        val orderRequest = Mono.just(
            PlaceOrderRequest(
                userId,
                Currency.USD,
                Address("street", "city", "state", "country", 10),
                "a&a.aa",
                CreditCardInfo("1234 1234 1234 1234", 123, 2030, 10)
            )
        )
        for (i in 1..10000000L) {
            webTestClient.post().uri("http://34.88.123.110:8080/basket")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(
                    basketItem, AddItemRequest::class.java
                )
                .exchange()
                .expectStatus().isOk

            webTestClient.post().uri("http://35.228.83.149:8083/order")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(
                    orderRequest, PlaceOrderRequest::class.java
                )
                .exchange()
                .expectStatus().isOk
        }

    }
}

data class AddItemRequest(
    val userId: UUID,
    val item: ItemDto
)

data class PlaceOrderRequest(
    val userId: UUID,
    val userCurrency: Currency,
    val address: Address,
    val email: String,
    val creditCard: CreditCardInfo,
)