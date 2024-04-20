package com.todoapp.demo.domain.model;

public class UserTask {
    private Long id;

    private Long idTask;

    private String idUser;

    public UserTask() {

    }

    public UserTask(Long id, Long idTask, String idUser) {
        this.id = id;
        this.idTask = idTask;
        this.idUser = idUser;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdTask() {
        return idTask;
    }

    public void setIdTask(Long idTask) {
        this.idTask = idTask;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }
}
