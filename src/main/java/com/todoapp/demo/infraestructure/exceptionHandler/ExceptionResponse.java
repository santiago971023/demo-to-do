package com.todoapp.demo.infraestructure.exceptionHandler;

public enum ExceptionResponse {
    //Mensajes personalizados

    USER_ALREADY_EXISTS("There is already a user by id"),
    USER_NOT_FOUND("No user was found by id"),

    TASK_ALREADY_EXISTS("There is already a task by id"),

    TASK_NOT_FOUND("No task was found by id");



    private String message;

    ExceptionResponse(String message) {
        this.message = message;
    }

    public String getMessage(){
        return  this.message  = message;
    }
}
