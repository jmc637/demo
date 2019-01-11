package com.example.demo.appointment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query("SELECT a " +
            "FROM Appointment a " +
//            This line is to get all apppointments between or within this date range
            "WHERE ((a.appointmentStartDateTime BETWEEN ?1 AND ?2) OR (a.appointmentEndDateTime BETWEEN ?1 AND ?2) OR (a.appointmentStartDateTime <= ?1 AND a.appointmentEndDateTime >= ?2)) ")
    List<Appointment> findAppointmentsBetweenDateTimesAndPrices(LocalDateTime dateStartTime, LocalDateTime dateEndTime, Sort sort);
}
