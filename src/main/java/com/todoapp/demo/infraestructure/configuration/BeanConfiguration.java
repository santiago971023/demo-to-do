package com.todoapp.demo.infraestructure.configuration;

import com.todoapp.demo.domain.api.ITaskServicePort;
import com.todoapp.demo.domain.api.IUserServicePort;
import com.todoapp.demo.domain.api.IUserTaskServicePort;
import com.todoapp.demo.domain.spi.ITaskPersistencePort;
import com.todoapp.demo.domain.spi.IUserPersistencePort;
import com.todoapp.demo.domain.spi.IUserTaskPersistencePort;
import com.todoapp.demo.domain.usecase.TaskUseCase;
import com.todoapp.demo.domain.usecase.UserTaskUseCase;
import com.todoapp.demo.domain.usecase.UserUseCase;
import com.todoapp.demo.infraestructure.output.jpa.adapter.TaskJpaAdapter;
import com.todoapp.demo.infraestructure.output.jpa.adapter.UserJpaAdapter;
import com.todoapp.demo.infraestructure.output.jpa.adapter.UserTaskJpaAdapter;
import com.todoapp.demo.infraestructure.output.jpa.mapper.ITaskEntityMapper;
import com.todoapp.demo.infraestructure.output.jpa.mapper.IUserEntityMapper;
import com.todoapp.demo.infraestructure.output.jpa.mapper.IUserTaskEntityMapper;
import com.todoapp.demo.infraestructure.output.jpa.repository.ITaskRepository;
import com.todoapp.demo.infraestructure.output.jpa.repository.IUserRepository;
import com.todoapp.demo.infraestructure.output.jpa.repository.IUserTaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final IUserRepository userRepository;
    private final IUserEntityMapper userEntityMapper;

    private final ITaskRepository taskRepository;
    private final ITaskEntityMapper taskEntityMapper;

    private final IUserTaskRepository userTaskRepository;
    private final IUserTaskEntityMapper userTaskEntityMapper;




    @Bean
    public IUserPersistencePort userPersistencePort(){
        return new UserJpaAdapter(userRepository, userEntityMapper);
    }
    @Bean
    public IUserServicePort userServicePort(){
        return new UserUseCase(userPersistencePort());
    }

    @Bean
    public IUserTaskPersistencePort userTaskPersistencePort(){
        return new UserTaskJpaAdapter(userTaskRepository, userTaskEntityMapper);
    }

    @Bean
    public IUserTaskServicePort userTaskServicePort(){
        return new UserTaskUseCase(userTaskPersistencePort());
    }

    @Bean
    public ITaskPersistencePort taskPersistencePort(){
        return new TaskJpaAdapter(taskRepository, taskEntityMapper);
    }
    @Bean
    public ITaskServicePort taskServicePort(){
        return new TaskUseCase(taskPersistencePort());
    }

}
