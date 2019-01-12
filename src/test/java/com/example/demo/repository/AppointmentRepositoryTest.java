package com.example.demo.repository;

import com.example.demo.model.Appointment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class AppointmentRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AppointmentRepository appointmentRepository;

    private final Sort priceSortAsc = new Sort(Sort.Direction.ASC, "price");

    @Test
    public void findAppointmentsBetweenDateTimes() {
//        Save apppintment to database
        LocalDateTime appointmentStart = LocalDateTime.of(2019, 1, 1, 12, 0);
        LocalDateTime appointmentEnd= LocalDateTime.of(2019, 1, 1, 13, 0);
        Appointment appointment = new Appointment(appointmentStart, appointmentEnd, "Julius", true, BigDecimal.valueOf(100));
        entityManager.persist(appointment);
        entityManager.flush();

//        Make appointment object that was not persisted to check for false positive
        Appointment unpersistedAppointment = new Appointment(appointmentStart, appointmentEnd, "Unkown", false, BigDecimal.valueOf(0));

//        Test that the appointment exists and has correct data
        List<Appointment> appointmentList = appointmentRepository.findAppointmentsBetweenDateTimes(appointmentStart, appointmentEnd, this.priceSortAsc);
        Assertions.assertEquals(appointmentList.size(), 1);
        Appointment persistedAppointment = appointmentList.get(0);
        Assertions.assertEquals(appointment, persistedAppointment);
//        Checking for false positive
        Assertions.assertNotEquals(persistedAppointment, unpersistedAppointment);

//        Make sure appointment not returned for dates out of range
        LocalDateTime beforeAppointmentStart = LocalDateTime.of(2019, 1, 1, 0, 0);
        LocalDateTime beforeAppointmentStart1 = LocalDateTime.of(2019, 1, 1, 1, 0);
        appointmentList = appointmentRepository.findAppointmentsBetweenDateTimes(beforeAppointmentStart, beforeAppointmentStart1, this.priceSortAsc);
        Assertions.assertEquals(0, appointmentList.size());

//        Make sure appointment not returned for dates after
        LocalDateTime afterAppointmentEnd = LocalDateTime.of(2019, 1, 2, 0, 0);
        LocalDateTime afterAppointmentEnd1 = LocalDateTime.of(2019, 1, 3, 0, 0);
        appointmentList = appointmentRepository.findAppointmentsBetweenDateTimes(afterAppointmentEnd, afterAppointmentEnd1, this.priceSortAsc);
        Assertions.assertEquals( 0, appointmentList.size());

//        Make sure appointment multiple appointments are sorted by price correctly
        Appointment appointment2 =  new Appointment(beforeAppointmentStart, beforeAppointmentStart1, "Laurel", true, BigDecimal.valueOf(50));
        entityManager.persist(appointment2);
        entityManager.flush();
        appointmentList = appointmentRepository.findAppointmentsBetweenDateTimes(beforeAppointmentStart, appointmentEnd, this.priceSortAsc);
        Assertions.assertEquals( 2, appointmentList.size());
        BigDecimal earlierAppointmentPrice = appointmentList.get(0).getPrice();
        BigDecimal laterAppointmentPrice = appointmentList.get(1).getPrice();

        Assertions.assertEquals(-1, earlierAppointmentPrice.compareTo(laterAppointmentPrice));
    }
}
