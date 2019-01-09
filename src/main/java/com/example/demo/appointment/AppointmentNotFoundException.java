package com.example.demo.appointment;

class AppointmentNotFoundException extends RuntimeException {

    AppointmentNotFoundException(Long id) {
        super("Could not find appointment" + id);
    }
}
