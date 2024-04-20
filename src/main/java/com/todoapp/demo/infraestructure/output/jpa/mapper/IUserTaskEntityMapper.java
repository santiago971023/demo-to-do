package com.todoapp.demo.infraestructure.output.jpa.mapper;

import com.todoapp.demo.domain.model.UserTask;
import com.todoapp.demo.infraestructure.output.jpa.entities.UserTaskEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IUserTaskEntityMapper {

    UserTaskEntity toEntity(UserTask userTask);

    UserTask toUserTask(UserTaskEntity userTaskEntity);

    List<UserTask> toUserTaskList(List<UserTaskEntity> userTaskEntityList);

}
