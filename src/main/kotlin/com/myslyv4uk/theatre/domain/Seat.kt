package com.myslyv4uk.theatre.domain

import java.math.BigDecimal
import javax.persistence.*

@Entity
data class Seat(
        @Id @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Long,
        val rowSeat: Char,
        val num: Int,
        val price: BigDecimal,
        val description: String
) {
    @OneToMany
    lateinit var booking: List<Booking>
    override fun toString(): String = "Seat $rowSeat-$num $$price ($description)"
}