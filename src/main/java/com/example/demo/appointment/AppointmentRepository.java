package com.example.demo.appointment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Sort;

import java.util.Date;
import java.util.List;

interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query("SELECT a " +
            "FROM Appointment a " +
//            This line is to get all apppointments between or within this date range
            "WHERE ((a.appointmentStartDate BETWEEN ?1 AND ?2) OR (a.appointmentEndDate BETWEEN ?1 AND ?2) OR (a.appointmentStartDate <= ?1 AND a.appointmentEndDate >= ?2)) ")
    List<Appointment> findAppointmentsBetweenDateTimesAndPrices(Date dateStart, Date dateEnd, Sort sort);
}
