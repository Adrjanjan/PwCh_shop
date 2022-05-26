package pl.edu.agh.pwch.shop.payment.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import pl.edu.agh.pwch.shop.shareddto.payment.CreditCardInfo
import pl.edu.agh.pwch.shop.shareddto.payment.Money
import java.util.*
import javax.persistence.Embeddable
import javax.persistence.Entity
import javax.persistence.Id

@Repository
interface TransactionRepository : JpaRepository<Transaction, UUID>

@Entity
class Transaction(
    @Id
    val id: UUID,
    val amount: Money,
    val creditCardInfo: CreditCardInfo
)
