package com.example.demo.appointment;

class AppointmentCreatorThread implements Runnable {
    @Override
    public void run() {
        while(true) {
            System.out.println("APPOINTMENT CREATOR THREAD RUNNING");
        }
    }
}
