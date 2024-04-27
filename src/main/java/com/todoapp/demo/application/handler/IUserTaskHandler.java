package com.todoapp.demo.application.handler;

import java.util.List;

public interface IUserTaskHandler {


    void removeTask(String userToRemoveId, Long idTaskToRemove, String updaterId);

    void assignTask(String userToAssignId, Long idTaskToAssign, String updaterId);
    List<String> getUsersIdsByTask(Long idTask);

    List<Long> getTaskIdsByUser(String idUser);



}
