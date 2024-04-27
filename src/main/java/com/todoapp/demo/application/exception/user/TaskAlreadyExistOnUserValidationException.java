package com.todoapp.demo.application.exception.user;

public class TaskAlreadyExistOnUserValidationException extends UserValidationException{
    public TaskAlreadyExistOnUserValidationException(String message) {
        super(message);
    }
}
