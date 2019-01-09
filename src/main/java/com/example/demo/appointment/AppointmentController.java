package com.example.demo.appointment;

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
            @RequestParam("dateStart") Date dateStart,
            @RequestParam("dateEnd") Date dateEnd,
            @RequestParam("price[gte]") BigDecimal priceLowerBound,
            @RequestParam("price[lte]") BigDecimal priceUpperBound
    ) {
//        In this case we have all query params we need to do search with a date range and price range. This is assumes
//        we need all query params but this could be made more flexible in future.
//        if(dateStart != null && dateEnd != null && priceLowerBound != null && priceUpperBound != null) {

//        } else {
            return repository.findAll();
//        }
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

//    Temp code to print all fields in object
    void printAllFields(Object object) {
        for (Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            String name = field.getName();
            try {
                Object value = field.get(object);
                System.out.printf("Field name: %s, Field value: %s%n", name, value);
            } catch (IllegalAccessException e) {
                System.out.printf("Couldn't print Field name: %s", name);
            }
        }
    }
}
