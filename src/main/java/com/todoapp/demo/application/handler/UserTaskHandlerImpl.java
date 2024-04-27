package com.todoapp.demo.application.handler;

import com.todoapp.demo.application.mapper.IUserTaskMapper;
import com.todoapp.demo.domain.api.IUserTaskServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserTaskHandlerImpl implements IUserTaskHandler{

    private final IUserTaskServicePort userTaskServicePort;
    @Override
    public void assignUser(String userId, Long taskId) {
        userTaskServicePort.assignUser(taskId, userId);
    }

    @Override
    public void removeUser(String userId, Long taskId) {
        userTaskServicePort.removeUser(taskId, userId);
    }

    @Override
    public List<String> getUsersIdsByTask(Long idTask) {
        return userTaskServicePort.getUsersByTaskId(idTask);
    }

    @Override
    public List<Long> getTaskIdsByUser(String idUser) {
        return userTaskServicePort.getTasksByUserId(idUser);
    }
}
