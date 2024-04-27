package com.todoapp.demo.application.exception.task;


public class CantUpdateTaskValidationException extends TaskValidationException {
    public CantUpdateTaskValidationException(String message) {
        super(message);
    }
}
