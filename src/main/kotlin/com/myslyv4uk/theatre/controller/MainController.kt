package com.myslyv4uk.theatre.controller

import com.myslyv4uk.theatre.data.BookingRepository
import com.myslyv4uk.theatre.data.PerformanceRepository
import com.myslyv4uk.theatre.data.SeatRepository
import com.myslyv4uk.theatre.domain.Booking
import com.myslyv4uk.theatre.domain.Performance
import com.myslyv4uk.theatre.domain.Seat
import com.myslyv4uk.theatre.service.BookingService
import com.myslyv4uk.theatre.service.TheaterService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.servlet.ModelAndView

@Controller
class MainController {

    @Autowired
    lateinit var theaterService: TheaterService

    @Autowired
    lateinit var bookingService: BookingService

    @Autowired
    lateinit var seatRepository: SeatRepository

    @Autowired
    lateinit var performanceRepository: PerformanceRepository

    @RequestMapping("")
    fun homePage(): ModelAndView {
        val model = mapOf("bean" to CheckAvailabilityBackingBean(),
                "performances" to performanceRepository.findAll(),
                "seatNums" to 1..36,
                "seatRows" to 'A'..'O'
        )
        return ModelAndView("seatBooking", model)
    }

    @RequestMapping("/checkAvailability", method = [RequestMethod.POST])
    fun checkAvailability(bean: CheckAvailabilityBackingBean): ModelAndView {
        val selectedSeat = seatRepository.findAll().last { it.rowSeat == bean.selectedSeatRow && it.num == bean.selectedSeatNum }
        val selectedPerformance = performanceRepository.findAll().last { it.id == bean.selectedPerformance }
        bean.seat = selectedSeat
        bean.performance = selectedPerformance
        val seatFree = bookingService.isSeatFree(selectedSeat, selectedPerformance)
        bean.available = seatFree
        if (!seatFree) {
            bean.booking = bookingService.findBooking(selectedSeat, selectedPerformance)
        }
        val model = mapOf("bean" to bean,
                "performances" to performanceRepository.findAll(),
                "seatNums" to 1..36,
                "seatRows" to 'A'..'O'
        )
        return ModelAndView("seatBooking", model)
    }

    @RequestMapping("bootstrap")
    fun createInitialData(): ModelAndView {
        //create the data and save it to the database
        val seats = theaterService.seats
        seatRepository.saveAll(seats);
        return homePage()
    }

    @RequestMapping(value = ["booking"], method = [RequestMethod.POST])
    fun bookASeat(bean: CheckAvailabilityBackingBean): ModelAndView {
        val booking = bookingService.reserveSeat(bean.seat!!, bean.performance!!, bean.customerName)
        return ModelAndView("bookingConfirmed", "booking", booking)
    }
}

class CheckAvailabilityBackingBean {
    var selectedSeatNum: Int = 1
    var selectedSeatRow: Char = 'A'
    var selectedPerformance: Long? = null
    var customerName: String = ""
    var available: Boolean? = null

    var seat: Seat? = null
    var performance: Performance? = null
    var booking: Booking? = null
}