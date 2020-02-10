package com.jovo.ScienceCenter.exception;


public class EmptyFileException extends RuntimeException {

    public EmptyFileException() {
    }

    public EmptyFileException(String message) {
        super(message);
    }
}