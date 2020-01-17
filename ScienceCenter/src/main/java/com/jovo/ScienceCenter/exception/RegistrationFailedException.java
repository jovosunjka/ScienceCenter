package com.jovo.ScienceCenter.exception;


public class RegistrationFailedException extends RuntimeException {

    public RegistrationFailedException() {
    }

    public RegistrationFailedException(String message) {
        super(message);
    }
}