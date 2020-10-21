package com.myslyv4uk.theatre.service

import com.myslyv4uk.theatre.data.BookingRepository
import com.myslyv4uk.theatre.domain.Booking
import com.myslyv4uk.theatre.domain.Performance
import com.myslyv4uk.theatre.domain.Seat
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class BookingService {

    @Autowired
    lateinit var bookingRepository: BookingRepository

    fun isSeatFree(seat: Seat, performance: Performance): Boolean {
        return findBooking(seat, performance) == null
    }

    fun reserveSeat(seat: Seat, performance: Performance, customerName: String): Booking {
        val booking = Booking(0, customerName)
        booking.seat = seat
        booking.performance = performance
        return bookingRepository.save(booking)
    }

    fun findBooking(selectedSeat: Seat, selectedPerformance: Performance): Booking? {
        return bookingRepository.findAll()
                .firstOrNull { it.seat == selectedSeat && it.performance == selectedPerformance }
    }
}