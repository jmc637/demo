package com.example.demo.appointment;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
class RandomAppointmentCreatorTask implements Runnable {

    private final Random random = new Random();

    private final String[] DOCTOR_NAMES =  { "Bob", "Jim", "Anne", "Mary" };

    private final AppointmentRepository appointmentRepository;

    RandomAppointmentCreatorTask(AppointmentRepository appointmentRepository) {
        this.appointmentRepository  = appointmentRepository;
    }

    LocalDateTime[] generateRandomLocalDateTimesOffsetBy1Hour() {
        Long minDateTimeInSeconds= LocalDateTime.of(2019, 2, 1, 0, 0).toEpochSecond(ZoneOffset.UTC);
        Long maxDateTimeInSeconds = LocalDateTime.of(2020, 2, 1, 0, 0).toEpochSecond(ZoneOffset.UTC);
        Long randomEpochTimeInSeconds = ThreadLocalRandom.current().nextLong(minDateTimeInSeconds, maxDateTimeInSeconds);
        Long randomEpochTimePlusAnHourInSeoncds = randomEpochTimeInSeconds + 3600;
        LocalDateTime randomDateTime = LocalDateTime.ofEpochSecond(randomEpochTimeInSeconds, 0, ZoneOffset.UTC);
        LocalDateTime randomDateTimePlusAnHour = LocalDateTime.ofEpochSecond(randomEpochTimePlusAnHourInSeoncds, 0, ZoneOffset.UTC);
        return new LocalDateTime[]{ randomDateTime, randomDateTimePlusAnHour };
    }

    @Override
    public void run() {
        LocalDateTime[] randomDateTimes = this.generateRandomLocalDateTimesOffsetBy1Hour();
        LocalDateTime appointmentStartDateTime = randomDateTimes[0];
        LocalDateTime appointmentEndDateTime = randomDateTimes[1];
        String doctorName = DOCTOR_NAMES[random.nextInt(DOCTOR_NAMES.length)];
        Boolean status = true;
        BigDecimal price = BigDecimal.valueOf(random.nextInt(500) + 50);
        appointmentRepository.save(new Appointment(appointmentStartDateTime, appointmentEndDateTime, doctorName, status, price));
        log.info("CREATED APPOINTMENT FOR DOCTOR " + doctorName + " AT " + appointmentStartDateTime + " UNTIL " + appointmentEndDateTime);
    }
}
