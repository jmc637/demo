package com.example.demo.controller;

import com.example.demo.controller.exception.AppointmentNotFoundException;
import com.example.demo.controller.exception.DateTimeBoundIncorrectException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

// To handle exceptions thrown in controller and send useful messages to users of api
@ControllerAdvice
public class AppointmentControllerAdvice {

    @ResponseBody
    @ExceptionHandler(AppointmentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String appointmentNotFoundHandler(AppointmentNotFoundException e) {
        return e.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(DateTimeBoundIncorrectException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String dateTimeBoundIncorrectHandler(DateTimeBoundIncorrectException e) {
        return e.getMessage();
    }

}
