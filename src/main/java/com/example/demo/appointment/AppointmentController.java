package com.example.demo.appointment;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@RestController
class AppointmentController {

    private final AppointmentRepository repository;

    AppointmentController(AppointmentRepository repository) {
        this.repository = repository;
    }


    @GetMapping("/appointments")
    List<Appointment> getAppointments(
            @RequestParam(value = "dateStart", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date dateStart,
            @RequestParam(value = "dateEnd", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date dateEnd,
            @RequestParam(value = "price[gte]", required = false) BigDecimal priceLowerBound,
            @RequestParam(value = "price[lte]", required = false) BigDecimal priceUpperBound
    ) {
//        In this case we have all query params we need to do search with a date range and price range. This is assumes
//        we need all query params but this could be made more flexible in future.
        if(dateStart != null && dateEnd != null && priceLowerBound != null && priceUpperBound != null) {
            if(dateStart.compareTo(dateEnd) > 0) {
                throw new DateBoundIncorrectException();
            }
            return repository.findAppointmentsBetweenDateTimes(dateStart, dateEnd, priceLowerBound, priceUpperBound);
        } else {
            return repository.findAll();
        }
    }

    @PostMapping("/appointments")
    Appointment createAppointment(@RequestBody Appointment newAppointment) {
        return repository.save(newAppointment);
    }

    @GetMapping("/appointments/{id}")
    Appointment getAppointment(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new AppointmentNotFoundException(id));
    }

    @PatchMapping("/appointments/{id}")
    Appointment updateAppointment(@RequestBody Appointment newAppointment, @PathVariable Long id) {
        return repository.findById(id)
                .map(appointment -> {
//                    Many null checks in case user sends fields that are blank. Looking for way to refactor...
                    if(newAppointment.getAppointmentStartDate() != null)
                        appointment.setAppointmentStartDate(newAppointment.getAppointmentStartDate());
                    if(newAppointment.getAppointmentEndDate() != null)
                        appointment.setAppointmentEndDate(newAppointment.getAppointmentEndDate());
                    if(newAppointment.getNameOfDoctor() != null)
                        appointment.setNameOfDoctor(newAppointment.getNameOfDoctor());
                    if(newAppointment.getStatus() != null)
                        appointment.setStatus(newAppointment.getStatus());
                    if(newAppointment.getPrice() != null)
                        appointment.setPrice(newAppointment.getPrice());
                    return repository.save(appointment);
                })
                .orElseThrow(() -> new AppointmentNotFoundException(id));
    }

    @DeleteMapping("/appointments/{id}")
    void deleteAppointment(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
