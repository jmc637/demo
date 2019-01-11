package com.example.demo.controller.exception;

public class AppointmentNotFoundException extends RuntimeException {

    public AppointmentNotFoundException(Long id) {
        super("Could not find appointment" + id);
    }
}
