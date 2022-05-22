package pl.edu.agh.pwch.shop.notification

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.cloud.spring.pubsub.support.converter.JacksonPubSubMessageConverter
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean


@SpringBootApplication
class NotificationApplication{
    @Bean
    fun jacksonPubSubMessageConverter(objectMapper: ObjectMapper): JacksonPubSubMessageConverter {
        return JacksonPubSubMessageConverter(objectMapper)
    }
}

fun main(args: Array<String>) {
    runApplication<NotificationApplication>(*args)
}
