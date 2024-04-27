package com.todoapp.demo.application.exception.task;

public class TaskValidationException extends RuntimeException{

    public TaskValidationException(String message){
        super(message);
    }

}
