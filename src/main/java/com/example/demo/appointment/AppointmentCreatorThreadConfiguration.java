package com.example.demo.appointment;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

@Configuration
@Slf4j
class AppointmentCreatorThreadConfiguration {

    @Bean
    public TaskExecutor taskExecutor() {
        return new SimpleAsyncTaskExecutor();
    }

    @Bean
    CommandLineRunner startAppointmentCreator(TaskExecutor taskExecutor) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                taskExecutor.execute(new AppointmentCreatorThread());
            }
        };
    }
}
