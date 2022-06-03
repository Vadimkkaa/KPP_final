package com.example.restservice.exception;

public class WrongInputFormat extends Exception {
    public WrongInputFormat() {
        super();
    }

    public WrongInputFormat(String message) {
        super(message);
    }
}
