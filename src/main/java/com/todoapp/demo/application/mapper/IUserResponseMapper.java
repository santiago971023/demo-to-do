package com.todoapp.demo.application.mapper;

import com.todoapp.demo.application.dto.response.UserResponseDto;
import com.todoapp.demo.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;


@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IUserResponseMapper {

    UserResponseDto toUserResponse(User user);

    List<UserResponseDto> toUserResponseList(List<User> userList);
}
