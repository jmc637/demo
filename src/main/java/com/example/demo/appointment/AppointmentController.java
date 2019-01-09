package com.example.demo.appointment;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
class AppointmentController {

    private final AppointmentRepository repository;

    AppointmentController(AppointmentRepository repository) {
        this.repository = repository;
    }


    @GetMapping("/appointments")
    List<Appointment> getAllAppointments() {
        return repository.findAll();
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

//    @PutMapping("/appointments/{id}")
//    Appointment updateAppointment(@RequestBody Appointment newAppointment, @PathVariable Long id) {
//        return repository.findById(id)
//                .map(appointment -> {
//                    appointment.setName(newAppointment.getName());
//                    appointment.setRole(newAppointment.getRole());
//                    return repository.save(appointment);
//                })
//                .orElseGet(() -> {
//                    newAppointment.setId(id);
//                    return repository.save(newAppointment);
//                });
//    }

    @DeleteMapping("/appointments/{id}")
    void deleteAppointment(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
