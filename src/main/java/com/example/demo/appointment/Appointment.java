package com.example.demo.appointment;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import java.math.BigDecimal;
import java.time.Period;
import java.util.Date;
import java.util.function.Consumer;

@Data
@Entity
@NoArgsConstructor
class Appointment {

//    Not editable from api
    private @Id @GeneratedValue Long id;

//    Not editable from api
    private Date createdAt;

    private Date appointmentStartDate;

    private Date appointmentEndDate;

    private String nameOfDoctor;

    private Boolean status;

    private BigDecimal price;

    @PrePersist
    void onCreate() {
        this.createdAt = new Date();
    }

    Appointment(Date appointmentStartDate, Date appointmentEndDate, String nameOfDoctor, boolean status, BigDecimal price) {
        this.appointmentStartDate = appointmentStartDate;
        this.appointmentEndDate = appointmentEndDate;
        this.nameOfDoctor = nameOfDoctor;
        this.status = status;
        this.price = price;
    }
}
