package com.todoapp.demo.domain.usecase;

import com.todoapp.demo.domain.api.IUserTaskServicePort;
import com.todoapp.demo.domain.spi.IUserTaskPersistencePort;

import java.util.List;

public class UserTaskUseCase implements IUserTaskServicePort {

    private final IUserTaskPersistencePort userTaskPersistencePort;

    public UserTaskUseCase(IUserTaskPersistencePort userTaskPersistencePort) {
        this.userTaskPersistencePort = userTaskPersistencePort;

    }



    @Override
    public void removeUser(Long taskId, String userId) {


        userTaskPersistencePort.removeUser(taskId, userId);
    }

    @Override
    public void assignUser(Long taskId, String userId) {


        userTaskPersistencePort.assignUser(taskId, userId);
    }

    @Override
    public List<Long> getTasksByUserId(String userId) {
        return userTaskPersistencePort.getTasksByUserId(userId);
    }

    @Override
    public List<String> getUsersByTaskId(Long taskId) {
        return userTaskPersistencePort.getUsersByTaskId(taskId);
    }
}
