package com.myslyv4uk.theatre.data;

import com.myslyv4uk.theatre.domain.Booking
import com.myslyv4uk.theatre.domain.Performance
import com.myslyv4uk.theatre.domain.Seat
import org.springframework.data.jpa.repository.JpaRepository

interface SeatRepository : JpaRepository<Seat, Long>

interface PerformanceRepository: JpaRepository<Performance, Long>

interface BookingRepository: JpaRepository<Booking, Long>
