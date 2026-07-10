package com.todoapp.dto;

import com.todoapp.model.Status;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class TaskResponse {
    private Long id;
    private String name;
    private String description;
    private LocalDate tentativeEndDate;
    private LocalDate actualEndDate;
    private String vibe;
    private Status status;

}