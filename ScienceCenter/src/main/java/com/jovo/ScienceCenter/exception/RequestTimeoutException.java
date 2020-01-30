package com.jovo.ScienceCenter.exception;


public class RequestTimeoutException extends RuntimeException {

    public RequestTimeoutException() {
    }

    public RequestTimeoutException(String message) {
        super(message);
    }
}