package pl.edu.agh.pwch.shop.notification.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import pl.edu.agh.pwch.shop.notification.model.SentEmail
import java.util.*

@Repository
interface SentEmailRepository : CrudRepository<SentEmail, UUID> {
}