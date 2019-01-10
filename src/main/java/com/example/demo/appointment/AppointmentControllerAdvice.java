package com.example.demo.appointment;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class AppointmentControllerAdvice {

    @ResponseBody
    @ExceptionHandler(AppointmentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String appointmentNotFoundHandler(AppointmentNotFoundException e) {
        return e.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(DateBoundIncorrectException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String dateBoundInccorectHandler(DateBoundIncorrectException e) {
        return e.getMessage();
    }

}
