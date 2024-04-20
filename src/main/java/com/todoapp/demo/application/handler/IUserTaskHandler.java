package com.todoapp.demo.application.handler;

import java.util.List;

public interface IUserTaskHandler {


    void assignUser(String userId, Long taskId);
    void removeUser(String userId, Long taskId);

    List<String> getUsersIdsByTask(Long idTask);

    List<Long> getTaskIdsByUser(String idUser);



}
