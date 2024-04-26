 package com.todoapp.demo.application.exception;

 import lombok.Getter;

 @Getter
 public enum ErrorMessagesApplication {

    //  USERS
    CANT_CREATE("No tienes permisos para crear un nuevo usuario"),
    CANT_REMOVE_TASK("No tienes permisos para remover esta tarea a este usuario"),
    CANT_ASSIGN_TASK("No tienes permisos para asignar esta tarea a este usuario"),
    CANT_DELETE("No tienes permisos para eliminar este usuario"),
    CANT_UPDATE("No tienes permisos para actualizar este usuario"),

    //   TASK

    CANT_CREATE_TASK("No tienes permisos para crear un task nuevo"),
    CANT_UPDATE_TASK("No tienes permisos para actualizar un task"),
    CANT_DELETE_TASK("No tienes permisos para eliminar un task o el task que intentas eliminar no existe."),

    TASK_ALREADY("La tarea ya existe."),
    ID_USER_NOTFOUND_IN_TASK("El id de usuario obtenido no coincide con alguno de los id de usuarios del task."),
    ID_USER_ALREADY_EXIST_ON_TASK("Este usuario ya está asignado a este task"),
    CANT_UPDATE_STATUS_TASK("No tienes permiso para actualizar el estado de la tarea");




    private final String message;

    ErrorMessagesApplication(String message) {
        this.message = message;
    }

 }
