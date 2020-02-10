package com.jovo.ScienceCenter.exception;


public class AuthorHasNotActivatedMembershipFeeException extends RuntimeException {

    public AuthorHasNotActivatedMembershipFeeException() {
    }

    public AuthorHasNotActivatedMembershipFeeException(String message) {
        super(message);
    }
}