package com.example.demo.controller.exception;

public class DateTimeBoundIncorrectException extends RuntimeException {

    public DateTimeBoundIncorrectException() {
        super("Datetime start must be before datetime end!");
    }
}
