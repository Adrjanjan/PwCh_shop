package pl.edu.agh.pwch.notification.shared.model

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty


data class EmailDto @JsonCreator constructor(
    @param:JsonProperty("sender") val sender: String,
    @param:JsonProperty("receiver") val receiver: String,
    @param:JsonProperty("content") val content: String,
    @param:JsonProperty("subject") val subject: String,
)
