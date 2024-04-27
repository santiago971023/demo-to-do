package com.todoapp.demo.infraestructure.output.jpa.mapper;

import com.todoapp.demo.domain.model.Task;
import com.todoapp.demo.infraestructure.output.jpa.entities.TaskEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ITaskEntityMapper {


    TaskEntity toEntity(Task task);


    Task toTask(TaskEntity taskEntity);

    List<Task> toTaskList(List<TaskEntity> taskEntityLis);
}
