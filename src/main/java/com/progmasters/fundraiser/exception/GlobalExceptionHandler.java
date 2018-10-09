package com.progmasters.fundraiser.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    private MessageSource messageSource;

    @Autowired
    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    protected ResponseEntity<ValidationError> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();

        return new ResponseEntity<>(processFieldErrors(fieldErrors), HttpStatus.BAD_REQUEST);
    }

    private ValidationError processFieldErrors(List<FieldError> fieldErrors) {
        ValidationError dto = new ValidationError();

        for (FieldError fieldError: fieldErrors) {
            dto.addFieldError(fieldError.getField(), messageSource.getMessage(fieldError, null));
        }

        return dto;
    }

    @ExceptionHandler(ConfirmationCodeException.class)
    @ResponseBody
    protected ResponseEntity<ConfirmationCodeError> handleConfirmationCodeNotValid(ConfirmationCodeException ex) {
        ConfirmationCodeError confirmationCodeError = new ConfirmationCodeError(ex.getMessage());

        return new ResponseEntity<>(confirmationCodeError, HttpStatus.BAD_REQUEST);
    }
}
