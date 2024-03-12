package com.todoapp.demo.domain.exception;

public class TaskValidationException extends RuntimeException {

    public TaskValidationException(String message) {
        super(message);
    }
}
