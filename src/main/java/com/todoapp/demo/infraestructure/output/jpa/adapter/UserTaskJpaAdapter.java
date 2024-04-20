package com.todoapp.demo.infraestructure.output.jpa.adapter;

import com.todoapp.demo.domain.spi.IUserTaskPersistencePort;
import com.todoapp.demo.infraestructure.output.jpa.mapper.IUserTaskEntityMapper;
import com.todoapp.demo.infraestructure.output.jpa.repository.IUserTaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
public class UserTaskJpaAdapter implements IUserTaskPersistencePort {

    private final IUserTaskRepository userTaskRepository;
    private final IUserTaskEntityMapper userTaskEntityMapper;

    @Override
    public void removeUser(Long taskId, String userId) {
        userTaskRepository.removeTaskFromUser(userId, taskId);
    }

    @Override
    public void assignUser(Long taskId, String userId) {
        userTaskRepository.assignTaskToUser(userId, taskId);
    }

    @Override
    public List<Long> getTasksByUserId(String userId) {
        return userTaskRepository.findTaskIdsByUserId(userId);
    }

    @Override
    public List<String> getUsersByTaskId(Long taskId) {
        return userTaskRepository.findUserIdsByTaskId(taskId);
    }
}
