package com.progmasters.fundraiser.exception;

public class ConfirmationCodeError {

    private String message;

    public ConfirmationCodeError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
