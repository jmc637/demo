package com.example.demo.appointment;

class DateTimeBoundIncorrectException extends RuntimeException {

    DateTimeBoundIncorrectException() {
        super("Datetime start must be before datetime end!");
    }
}
