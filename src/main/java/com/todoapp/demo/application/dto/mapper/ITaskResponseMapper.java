package com.todoapp.demo.application.dto.mapper;

import com.todoapp.demo.application.dto.response.TaskResponseDto;
import com.todoapp.demo.domain.model.Task;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ITaskResponseMapper {

    TaskResponseDto toTaskResponse(Task task);

    List<TaskResponseDto> toTaskResponseList(List<Task> taskList);
}
