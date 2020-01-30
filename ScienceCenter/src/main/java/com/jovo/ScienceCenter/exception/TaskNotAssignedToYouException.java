package com.jovo.ScienceCenter.exception;


public class TaskNotAssignedToYouException extends RuntimeException {

    public TaskNotAssignedToYouException() {
    }

    public TaskNotAssignedToYouException(String message) {
        super(message);
    }
}