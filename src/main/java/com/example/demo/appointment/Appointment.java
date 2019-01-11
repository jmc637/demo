package com.example.demo.appointment;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
class Appointment {

//    Not editable from api
    private @Id @GeneratedValue Long id;

//    Not editable from api
    private Date createdAt;

    private LocalDateTime appointmentStartDateTime;

    private LocalDateTime appointmentEndDateTime;

    private String nameOfDoctor;

    private Boolean status;

    private BigDecimal price;

    @PrePersist
    void onCreate() {
        this.createdAt = new Date();
    }

    Appointment(LocalDateTime appointmentStartDate, LocalDateTime appointmentEndDateTime, String nameOfDoctor, boolean status, BigDecimal price) {
        this.appointmentStartDateTime = appointmentStartDate;
        this.appointmentEndDateTime = appointmentEndDateTime;
        this.nameOfDoctor = nameOfDoctor;
        this.status = status;
        this.price = price;
    }
}
