package com.todoapp.demo.application.mapper;

import com.todoapp.demo.application.dto.request.TaskRequestDto;
import com.todoapp.demo.domain.model.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ITaskRequestMapper {


    Task toTask(TaskRequestDto taskRequestDto);

    List<Task> toTaskList(List<TaskRequestDto> taskRequestDtoList);


}
