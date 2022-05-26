package pl.edu.agh.pwch.shop.notification.service

import com.google.cloud.spring.pubsub.core.PubSubTemplate
import com.google.cloud.spring.pubsub.integration.AckMode
import com.google.cloud.spring.pubsub.integration.inbound.PubSubInboundChannelAdapter
import com.google.cloud.spring.pubsub.support.BasicAcknowledgeablePubsubMessage
import com.google.cloud.spring.pubsub.support.GcpPubSubHeaders
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.integration.annotation.ServiceActivator
import org.springframework.integration.channel.DirectChannel
import org.springframework.messaging.Message
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.MessageHandler
import org.springframework.stereotype.Service
import pl.edu.agh.pwch.shop.shareddto.notification.EmailDto


@Service
class NotificationMessageReceiver {

    @Bean
    fun pubSubInputChannel(): DirectChannel {
        return DirectChannel()
    }

    @Bean
    fun messageChannelAdapter(
        @Qualifier("pubSubInputChannel") inputChannel: MessageChannel,
        pubSubTemplate: PubSubTemplate
    ): PubSubInboundChannelAdapter {
        val adapter = PubSubInboundChannelAdapter(pubSubTemplate, SUBSCRIPTION_NAME)
        adapter.outputChannel = inputChannel
        adapter.ackMode = AckMode.MANUAL
        adapter.payloadType = EmailDto::class.java
        return adapter
    }

    @Bean
    @ServiceActivator(inputChannel = "pubsubInputChannel")
    fun messageReceiver(): MessageHandler? {
        return MessageHandler { message: Message<*> ->
            LOGGER.info("Message arrived! Payload: " + String((message.payload as ByteArray)))
            val originalMessage =
                message.headers.get(
                    GcpPubSubHeaders.ORIGINAL_MESSAGE,
                    BasicAcknowledgeablePubsubMessage::class.java
                )
            originalMessage!!.ack()
            message.payload as EmailDto
            // use email server to send email
        }
    }

    companion object {
        private val LOGGER: Log = LogFactory.getLog(NotificationMessageReceiver::class.java)
        private const val SUBSCRIPTION_NAME = "notification-subscription"
    }
}