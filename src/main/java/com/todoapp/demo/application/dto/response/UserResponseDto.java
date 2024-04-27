package com.todoapp.demo.application.dto.response;

import com.todoapp.demo.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {

    private String id;

    private String name;

    private String lastname;

    private String email;

    private String password;

    private Role role;

    private List<Long> tasks;
}
