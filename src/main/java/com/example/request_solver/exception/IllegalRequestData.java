package com.example.request_solver.exception;

public class IllegalRequestData extends RuntimeException {
    public IllegalRequestData(String message) {
        super(message);
    }
}