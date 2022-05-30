package pl.edu.agh.pwch.shop.order.model

import pl.edu.agh.pwch.shop.shareddto.payment.Money
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
class ShopOrder(
    @Id
    val id: UUID,

    @ManyToOne
    val orderedBy: User,

    val amount: Money,

    @Column
    val shippingTrackingId: UUID,
)