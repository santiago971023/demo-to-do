package com.todoapp.demo.application.dto.request;

import com.todoapp.demo.domain.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class UserRequestDto {

    @NotBlank(message = "Id field must not be empty")
    private String id;

    @NotBlank(message = "Name field must not be empty")
    private String name;

    @NotBlank(message = "Lastname field must not be empty")
    private String lastname;

    @NotBlank(message = "Email field must not be empty")
    @Email(message = "Email field must not be empty")
    private String email;

    @NotBlank(message = "Password field must not be empty")
    private String password;

    @NotBlank(message = "Role field must not be empty")
    private Role role;

}
