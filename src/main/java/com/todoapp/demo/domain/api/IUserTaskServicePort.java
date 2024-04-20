package com.todoapp.demo.domain.api;

import java.util.List;

public interface IUserTaskServicePort {

    void removeUser(Long taskId, String userId);

    void assignUser(Long taskId, String userId);

    List<Long> getTasksByUserId(String userId);

    List<String> getUsersByTaskId(Long taskId);

}
