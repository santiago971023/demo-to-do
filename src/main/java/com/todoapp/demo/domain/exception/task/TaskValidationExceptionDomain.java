package com.todoapp.demo.domain.exception.task;

public class TaskValidationExceptionDomain extends RuntimeException {

    public TaskValidationExceptionDomain(String message) {
        super(message);
    }
}
