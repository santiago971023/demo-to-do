package com.todoapp.demo.domain.exception;

public enum ErrorMessages {

    //  USERS
    ID_INVALID("Id cannot be null or empty."),
    NAME_INVALID("Name cannot be null or empty."),
    LASTNAME_INVALID("Lastname cannot be null or empty."),

    ROLE_INVALID("Invalid Role"),
    EMAIL_INVALID("The email cannot be empty, null or fail to comply with the format"),
    PASSWORD_INVALID("The password must contain at least one uppercase letter, one lowercase letter, one special " +
            "character and a number, in addition to having at least 8 characters."),
    IDTASK_INVALID("IdTask cannot be null or empty.");

    private final String message;

    ErrorMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
