package com.todoapp.demo.infraestructure.exceptionHandler;

import com.todoapp.demo.application.exception.ErrorMessagesApplication;
import com.todoapp.demo.application.exception.task.*;
import com.todoapp.demo.application.exception.user.*;
import com.todoapp.demo.domain.exception.ErrorMessagesDomain;
import com.todoapp.demo.domain.exception.task.*;
import com.todoapp.demo.domain.exception.user.*;
import com.todoapp.demo.infraestructure.exception.TaskAlreadyExistsException;
import com.todoapp.demo.infraestructure.exception.TaskNotFoundException;
import com.todoapp.demo.infraestructure.exception.UserAlreadyExistsException;
import com.todoapp.demo.infraestructure.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Collections;
import java.util.Map;

@ControllerAdvice
public class ControllerAdvisor {

    private static final String MESSAGE = "Message";

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleUserAlreadyExistsException(UserAlreadyExistsException userAlreadyExistsException) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.USER_ALREADY_EXISTS.getMessage()));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleNotDataFoundException(UserNotFoundException userNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.USER_NOT_FOUND.getMessage()));
    }

    @ExceptionHandler(TaskAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleTaskAlreadyExistsException(TaskAlreadyExistsException taskAlreadyExist) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.TASK_ALREADY_EXISTS.getMessage()));
    }

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleTaskNotDataFoundException(TaskNotFoundException taskNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.TASK_NOT_FOUND.getMessage()));
    }

    @ExceptionHandler(CantAssignTaskToUserValidationException.class)
    public ResponseEntity<Map<String, String>> handlerCantAssignValidationException(CantAssignTaskToUserValidationException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(MESSAGE, ErrorMessagesApplication.CANT_ASSIGN_TASK.getMessage()));
    }

    @ExceptionHandler(CantRemoveTaskFromUserValidationException.class)
    public ResponseEntity<Map<String, String>> handlerCantRemoveValidationException(CantRemoveTaskFromUserValidationException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(MESSAGE, ErrorMessagesApplication.CANT_REMOVE_TASK.getMessage()));
    }

    @ExceptionHandler(UserValidationException.class)
    public ResponseEntity<Map<String, String>> handlerUserApplicationException(UserValidationException e) {
        if(e instanceof CantCreateAnotherUserValidationException){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Collections.singletonMap(MESSAGE, ErrorMessagesApplication.CANT_CREATE.getMessage()));
        } else if(e instanceof CantUpdateAnotherUserValidationException){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Collections.singletonMap(MESSAGE, ErrorMessagesApplication.CANT_UPDATE.getMessage()));
        } else if(e instanceof CantDeleteAnotherUSerValidationException){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Collections.singletonMap(MESSAGE, ErrorMessagesApplication.CANT_DELETE.getMessage()));
        } else if(e instanceof CantAssignTaskToUserValidationException){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Collections.singletonMap(MESSAGE, ErrorMessagesApplication.CANT_ASSIGN_TASK.getMessage()));
        } else if(e instanceof CantRemoveTaskFromUserValidationException){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Collections.singletonMap(MESSAGE, ErrorMessagesApplication.CANT_REMOVE_TASK.getMessage()));
        } else if(e instanceof TaskAlreadyExistOnUserValidationException){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Collections.singletonMap(MESSAGE, ErrorMessagesApplication.ID_USER_ALREADY_EXIST_ON_TASK.getMessage()));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Collections.singletonMap(MESSAGE, "Error inesperado"));
    }

    @ExceptionHandler(TaskValidationException.class)
    public ResponseEntity<Map<String, String>> handlerTaskApplicationException(TaskValidationException e) {
        if (e instanceof CantCreateTaskValidationException) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Collections.singletonMap(MESSAGE, ErrorMessagesApplication.CANT_CREATE_TASK.getMessage()));
        } else if (e instanceof CantUpdateTaskValidationException) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Collections.singletonMap(MESSAGE, ErrorMessagesApplication.CANT_UPDATE_TASK.getMessage()));
        } else if (e instanceof CantDeleteTaskValidationException) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Collections.singletonMap(MESSAGE, ErrorMessagesApplication.CANT_DELETE_TASK.getMessage()));
        }else if(e instanceof CantUpdateStatusTaskValidationException){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Collections.singletonMap(MESSAGE, ErrorMessagesApplication.CANT_UPDATE_STATUS_TASK.getMessage()));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Collections.singletonMap(MESSAGE, "Error inesperado"));
    }



    @ExceptionHandler(UserValidationExceptionDomain.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<Map<String, String>> handlerUserDomainException(UserValidationExceptionDomain e) {
        if (e instanceof IdValidationExceptionDomain) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap(MESSAGE, ErrorMessagesDomain.ID_INVALID.getMessage()));
        } else if (e instanceof NameValidationExceptionDomain) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap(MESSAGE, ErrorMessagesDomain.NAME_INVALID.getMessage()));
        } else if (e instanceof LastNameValidationExceptionDomain) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap(MESSAGE, ErrorMessagesDomain.LASTNAME_INVALID.getMessage()));
        } else if (e instanceof EmailValidationExceptionDomain) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap(MESSAGE, ErrorMessagesDomain.EMAIL_INVALID.getMessage()));
        } else if (e instanceof PasswordValidationExceptionDomain) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap(MESSAGE, ErrorMessagesDomain.PASSWORD_INVALID.getMessage()));
        } else if (e instanceof RoleValidationExceptionDomain) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap(MESSAGE, ErrorMessagesDomain.ROLE_INVALID.getMessage()));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Collections.singletonMap(MESSAGE, "Error inesperado"));
    }

    @ExceptionHandler(TaskValidationExceptionDomain.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<Map<String, String>> handlerTaskDomainException(TaskValidationExceptionDomain e) {
        if (e instanceof TitleValidationExceptionDomain) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap(MESSAGE, ErrorMessagesDomain.TITLE_INVALID.getMessage()));
        } else if (e instanceof DescriptionValidationExceptionDomain) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap(MESSAGE, ErrorMessagesDomain.DESCRIPTION_INVALID.getMessage()));
        } else if (e instanceof FinishDateValidationExceptionDomain) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap(MESSAGE, ErrorMessagesDomain.FINISHDATE_INVALID.getMessage()));
        } else if (e instanceof StatusValidationExceptionDomain) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap(MESSAGE, ErrorMessagesDomain.STATUS_INVALID.getMessage()));
        } else if (e instanceof HistoryPointsValidationExceptionDomain) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap(MESSAGE, ErrorMessagesDomain.HISTORYPOINTS_NULL.getMessage()));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Collections.singletonMap(MESSAGE, "Error inesperado"));
    }
}
