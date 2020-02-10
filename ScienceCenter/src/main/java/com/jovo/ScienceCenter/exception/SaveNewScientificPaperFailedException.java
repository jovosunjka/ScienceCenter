package com.jovo.ScienceCenter.exception;

public class SaveNewScientificPaperFailedException extends RuntimeException {

    public SaveNewScientificPaperFailedException() {
    }

    public SaveNewScientificPaperFailedException(String message) {
        super(message);
    }
}
