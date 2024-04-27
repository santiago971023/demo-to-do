package com.todoapp.demo.application.exception.task;

public class CantUpdateStatusTaskValidationException extends TaskValidationException {
    public CantUpdateStatusTaskValidationException(String message) {
        super(message);
    }
}
