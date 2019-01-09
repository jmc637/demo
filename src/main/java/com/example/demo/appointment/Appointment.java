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

@Data
@Entity
@NoArgsConstructor
class Appointment {

    private @Id @GeneratedValue Long id;

    private Date createdAt;

    private Date appointmentStartDate;

    private Date appointmentEndDate;

    private String nameOfDoctor;

    private boolean status;

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
