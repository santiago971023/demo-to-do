package com.todoapp.demo.application.exception.task;


public class CantDeleteTaskValidationException extends TaskValidationException {
    public CantDeleteTaskValidationException(String message) {
        super(message);
    }
}
