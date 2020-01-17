package com.jovo.ScienceCenter.exception;


public class SaveNewMagazineFailedException extends RuntimeException {

    public SaveNewMagazineFailedException() {
    }

    public SaveNewMagazineFailedException(String message) {
        super(message);
    }
}