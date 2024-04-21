package com.todoapp.demo.domain.exception;

public class TaskValidationExceptionDomain extends RuntimeException {

    public TaskValidationExceptionDomain(String message) {
        super(message);
    }
}
