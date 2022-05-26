package pl.edu.agh.pwch.shop.order.model

import pl.edu.agh.pwch.shop.shareddto.currency.Currency
import pl.edu.agh.pwch.shop.shareddto.order.Address
import pl.edu.agh.pwch.shop.shareddto.payment.CreditCardInfo
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity
class User(
    @Id
    val id: UUID,

    @Column
    val currency: Currency,

    val address: Address,

    val email: String,

    val creditCard: CreditCardInfo,
)
