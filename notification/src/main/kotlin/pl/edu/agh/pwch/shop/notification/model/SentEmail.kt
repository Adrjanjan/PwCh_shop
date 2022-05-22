package pl.edu.agh.pwch.shop.notification.model

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id


@Entity
class SentEmail(
    @Id
    val id: UUID,

    @Column
    val sender: String,

    @Column
    val receiver: String,

    @Column
    val content: String,

    @Column
    val subject: String,
)
