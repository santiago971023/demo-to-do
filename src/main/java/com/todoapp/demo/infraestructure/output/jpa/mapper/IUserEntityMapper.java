package com.todoapp.demo.infraestructure.output.jpa.mapper;

import com.todoapp.demo.domain.model.User;
import com.todoapp.demo.infraestructure.output.jpa.entities.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;
@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IUserEntityMapper {

    UserEntity toEntity(User user);

    User toUser(UserEntity userEntity);

    List<User> toUserList(List<UserEntity> userEntityList);
}
