package com.example.demo.appointment;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ScheduledFuture;

@Configuration
@Slf4j
class SchedulerConfiguration {

    @Bean
    public TaskScheduler taskExecutor() {
        return new ThreadPoolTaskScheduler();
    }

    @Bean
    CommandLineRunner startAppointmentCreator(TaskScheduler taskScheduler, AppointmentRepository appointmentRepository) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
//                Create an appointment every x amount of time
                log.info("STARTED CREATING APPOINTMENTS");
                ScheduledFuture scheduledFuture = taskScheduler.scheduleWithFixedDelay(new RandomAppointmentCreatorTask(appointmentRepository), 1000);
//                    Cancel ongoing task after some amount of time
                new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            scheduledFuture.cancel(false);
                            log.info("STOPPED CREATING APPOINTMENTS");
                        }
                    }, 10000);
            }
        };
    }
}
