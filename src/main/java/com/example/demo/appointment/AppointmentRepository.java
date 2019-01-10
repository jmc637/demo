package com.example.demo.appointment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query("SELECT a " +
            "FROM Appointment a " +
//            This line is to get all apppointments between or within this date range
            "WHERE ((a.appointmentStartDate BETWEEN ?1 AND ?2) OR (a.appointmentEndDate BETWEEN ?1 AND ?2) OR (a.appointmentStartDate <= ?1 AND a.appointmentEndDate >= ?2)) " +
//            This line is to further filter by price range
            "AND (a.price BETWEEN ?3 AND ?4)")
    List<Appointment> findAppointmentsBetweenDateTimes(Date dateStart, Date dateEnd, BigDecimal priceLowerBound, BigDecimal priceUpperBound);
}
