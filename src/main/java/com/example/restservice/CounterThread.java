package com.example.restservice;

import com.example.restservice.controller.CounterController;

public class CounterThread extends Thread {

    public void run() {
        try {
            CounterController.incrementAndGet();
        } catch (Exception exception) {
            // Internal error
            MyLogger.error(exception.getMessage());
            throw exception;
        }
    }
}
