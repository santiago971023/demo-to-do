package com.todoapp.demo.application.dto.request;

import com.todoapp.demo.domain.Status;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskRequestDto {

    @NotBlank(message = "Id field must not be empty")
    private long id;

    @NotBlank(message = "Title field must not be empty")
    private String title;

    @NotBlank(message = "Description field must not be empty")
    private String description;

    @NotBlank(message = "StartDate field must not be empty")
    private LocalDate startDate;

    @NotBlank(message = "FinishDate field must not be empty")
    private LocalDate finishDate;

    @NotBlank(message = "Status field must not be empty")
    private Status status;

    @NotBlank(message = "History points field must not be empty")
    private Integer historyPoints;

    @Nullable
    private List<String> idUsers;
}
