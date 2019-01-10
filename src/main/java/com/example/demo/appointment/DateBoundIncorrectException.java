package com.example.demo.appointment;

class DateBoundIncorrectException extends RuntimeException {

    DateBoundIncorrectException() {
        super("Date start must be before date end!");
    }
}
