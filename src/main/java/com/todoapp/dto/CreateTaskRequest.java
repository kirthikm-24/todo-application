package com.todoapp.dto;

import com.todoapp.model.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class CreateTaskRequest {
    @NotBlank
    private String name;
    private String description;
    private LocalDate tentativeStartDate;
    @NotNull
    private LocalDate tentativeEndDate;
    private LocalDate actualStartDate;
    private LocalDate actualEndDate;
    private Status status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getTentativeStartDate() {
        return tentativeStartDate;
    }

    public void setTentativeStartDate(LocalDate tentativeStartDate) {
        this.tentativeStartDate = tentativeStartDate;
    }

    public LocalDate getTentativeEndDate() {
        return tentativeEndDate;
    }

    public void setTentativeEndDate(LocalDate tentativeEndDate) {
        this.tentativeEndDate = tentativeEndDate;
    }

    public LocalDate getActualStartDate() {
        return actualStartDate;
    }

    public void setActualStartDate(LocalDate actualStartDate) {
        this.actualStartDate = actualStartDate;
    }

    public LocalDate getActualEndDate() {
        return actualEndDate;
    }

    public void setActualEndDate(LocalDate actualEndDate) {
        this.actualEndDate = actualEndDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
