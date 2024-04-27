package com.todoapp.demo.application.dto.request;

import com.todoapp.demo.domain.Status;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskRequestDto {

    @NotBlank(message = "Id field must not be empty")
    private Long id;

    @NotBlank(message = "Title field must not be empty")
    private String title;

    @NotBlank(message = "Description field must not be empty")
    private String description;
/*
Nota: Como la fecha se inicaliza automaticamente, no es necesario que el usuario la ingrese, por lo que se comenta
    @NotBlank(message = "StartDate field must not be empty")
    private LocalDate startDate;
*/
    private LocalDate startDate = LocalDate.now();

    @NotBlank(message = "FinishDate field must not be empty")
    private LocalDate finishDate;

    @NotBlank(message = "Status field must not be empty")
    private Status status;

    @NotBlank(message = "History points field must not be empty")
    private Integer historyPoints;


}
