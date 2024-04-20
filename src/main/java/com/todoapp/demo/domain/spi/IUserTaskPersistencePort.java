package com.todoapp.demo.domain.spi;

import java.util.List;

public interface IUserTaskPersistencePort {

    void removeUser(Long taskId, String userId);

    void assignUser(Long taskId, String userId);

    List<Long> getTasksByUserId(String userId);

    List<String> getUsersByTaskId(Long taskId);

}
