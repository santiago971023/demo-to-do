package com.todoapp.demo.application.dto.mapper;


import com.todoapp.demo.application.dto.request.UserRequestDto;
import com.todoapp.demo.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)

public interface IUserRequestMapper {

    User toUser(UserRequestDto userRequestDto);

    List<User> toUserList(List<UserRequestDto> userRequestDtoList);

}
