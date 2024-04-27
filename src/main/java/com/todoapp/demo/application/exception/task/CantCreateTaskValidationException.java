package com.todoapp.demo.application.exception.task;

public class CantCreateTaskValidationException extends TaskValidationException{
    public CantCreateTaskValidationException(String message) {
        super(message);
    }
}
