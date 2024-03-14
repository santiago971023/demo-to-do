package com.todoapp.demo.application.dto.request;

import com.todoapp.demo.domain.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {

    @NotBlank(message = "Id field must not be empty")
    private String id;

    @NotBlank(message = "Name field must not be empty")
    private String name;

    @NotBlank(message = "Lastname field must not be empty")
    private String lastname;

    @Email(message = "Email field must not be empty")
    private String email;

    @NotBlank(message = "Password field must not be empty")
    private String password;

    @NotBlank(message = "Role field must not be empty")
    private Role role;

    @NotNull(message = "Tasks field must not be null")
    private List<Long> tasks;
}
