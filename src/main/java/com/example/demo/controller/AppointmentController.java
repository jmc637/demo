package com.example.demo.controller;

import com.example.demo.controller.exception.AppointmentNotFoundException;
import com.example.demo.repository.AppointmentRepository;
import com.example.demo.controller.exception.DateTimeBoundIncorrectException;
import com.example.demo.model.Appointment;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class AppointmentController {

    private final AppointmentRepository repository;

    AppointmentController(AppointmentRepository repository) {
        this.repository = repository;
    }


    @GetMapping("/appointments")
    List<Appointment> getAppointments(
            @RequestParam(value = "dateTimeStart", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTimeStart,
            @RequestParam(value = "dateTimeEnd", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTimeEnd
    ) {
//        In this case we have all query params we need to do search with a date range and price range. This is assumes
//        we need all query params but this could be made more flexible in future.
        if(dateTimeStart != null && dateTimeEnd != null) {
            if(dateTimeStart.compareTo(dateTimeEnd) > 0) {
                throw new DateTimeBoundIncorrectException();
            }
            return repository.findAppointmentsBetweenDateTimesAndPrices(dateTimeStart, dateTimeEnd, new Sort(Sort.Direction.ASC, "price"));
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
                    if(newAppointment.getAppointmentStartDateTime() != null)
                        appointment.setAppointmentStartDateTime(newAppointment.getAppointmentStartDateTime());
                    if(newAppointment.getAppointmentEndDateTime() != null)
                        appointment.setAppointmentEndDateTime(newAppointment.getAppointmentEndDateTime());
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
