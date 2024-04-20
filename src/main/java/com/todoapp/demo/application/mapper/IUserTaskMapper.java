package com.todoapp.demo.application.mapper;

import com.todoapp.demo.application.dto.UserTaskDto;
import com.todoapp.demo.domain.model.UserTask;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IUserTaskMapper {

    UserTask toUserTask(UserTaskDto userTaskDto);

    List<UserTask> toUserTaskList(List<UserTaskDto> userTaskDtoList);

}
