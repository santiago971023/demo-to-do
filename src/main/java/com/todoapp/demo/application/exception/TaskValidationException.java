package com.todoapp.demo.application.exception;

public class TaskValidationException extends RuntimeException{

    public TaskValidationException(String message){
        super(message);
    }

}
