package com.todoapp.demo.domain.exception;

public enum ErrorMessagesDomain {

    //  USERS
    ID_INVALID("Id cannot be null or empty."),
    NAME_INVALID("Name cannot be null or empty."),
    LASTNAME_INVALID("Lastname cannot be null or empty."),

    ROLE_INVALID("Invalid Role"),
    EMAIL_INVALID("The email cannot be empty, null or fail to comply with the format"),
    PASSWORD_INVALID("The password must contain at least one uppercase letter, one lowercase letter, one special " +
            "character and a number, in addition to having at least 8 characters."),

    //   TASK
    IDTASK_INVALID("IdTask cannot be null or empty."),
    TITLE_INVALID("The title cannot be null or empty. "),
    DESCRIPTION_INVALID("The description must contain more than 15 characters"),
    STARTDATE_INVALID("The startDate cannot be null or empty. "),

    FINISHDATE_INVALID("The finishDate cannot be null or empty, neither before the initial date."),
    STATUS_INVALID("The status cannot be null or empty."),
    HISTORYPOINTS_NULL("The value of history points cannot be null or empty, and must be an integer.");


    private final String message;

    ErrorMessagesDomain(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
