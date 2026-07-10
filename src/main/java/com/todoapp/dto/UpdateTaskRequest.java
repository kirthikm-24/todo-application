package com.todoapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class UpdateTaskRequest {
    @NotBlank
    private String name;
    private String description;
    @NotNull
    private LocalDate tentativeStartDate;
    @NotNull
    private LocalDate tentativeEndDate;
    private LocalDate actualStartDate;
    private LocalDate actualEndDate;

}
