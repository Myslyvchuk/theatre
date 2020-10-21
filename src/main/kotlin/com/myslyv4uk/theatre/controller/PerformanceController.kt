package com.myslyv4uk.theatre.controller

import com.myslyv4uk.theatre.data.PerformanceRepository
import com.myslyv4uk.theatre.domain.Performance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.servlet.ModelAndView


@Controller
@RequestMapping("/performances")
class PerformanceController() {

    @Autowired
    lateinit var performanceRepository: PerformanceRepository

    @RequestMapping("")
    fun performancesHomePage() =
            ModelAndView("performances/home", "performances", performanceRepository.findAll())

    @RequestMapping("/add")
    fun addPerformance() =
            ModelAndView("performances/add", "performance", Performance(0, ""))

    @RequestMapping(value = ["save"], method = arrayOf(RequestMethod.POST))
    fun savePerformance(performance: Performance): String {
        performanceRepository.save(performance)
        return "redirect:/performances/"
    }

}