package com.todoapp.demo.domain.exception;

public class PasswordValidationExceptionDomain extends  RuntimeException {

    public PasswordValidationExceptionDomain(String message) {
        super(message);
    }

}
