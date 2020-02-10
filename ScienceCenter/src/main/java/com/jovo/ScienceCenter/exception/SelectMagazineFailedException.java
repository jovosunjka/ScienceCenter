package com.jovo.ScienceCenter.exception;


public class SelectMagazineFailedException extends RuntimeException {

    public SelectMagazineFailedException() {
    }

    public SelectMagazineFailedException(String message) {
        super(message);
    }
}