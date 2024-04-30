package com.todoapp.demo.infraestructure.output.jpa.adapter;

import com.todoapp.demo.domain.model.UserTask;
import com.todoapp.demo.domain.spi.IUserTaskPersistencePort;
import com.todoapp.demo.infraestructure.exception.TaskNotFoundException;
import com.todoapp.demo.infraestructure.exception.UserAlreadyExistsException;
import com.todoapp.demo.infraestructure.exception.UserNotFoundException;
import com.todoapp.demo.infraestructure.output.jpa.entities.UserTaskEntity;
import com.todoapp.demo.infraestructure.output.jpa.mapper.IUserTaskEntityMapper;
import com.todoapp.demo.infraestructure.output.jpa.repository.IUserTaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.List;

@RequiredArgsConstructor
public class UserTaskJpaAdapter implements IUserTaskPersistencePort {

    private final IUserTaskRepository userTaskRepository;
    private final IUserTaskEntityMapper userTaskEntityMapper;

    @Override
    public void removeUser(Long taskId, String userId) {
        if (userTaskRepository.findByTaskIdAndUserId(taskId, userId).isEmpty()){
            throw new UserNotFoundException();
        }
        userTaskRepository.removeTaskFromUser(userId, taskId);
    }

    @Override
    public void assignUser(Long taskId, String userId) {
     if (userTaskRepository.findByTaskIdAndUserId(taskId, userId).isPresent()){
            throw new UserAlreadyExistsException();
        }
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
