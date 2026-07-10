package com.todoapp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@Entity
public class Task {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String description;
    private LocalDate tentativeStartDate;
    private LocalDate tentativeEndDate;
    private LocalDate actualStartDate;
    private LocalDate actualEndDate;

    @Enumerated(EnumType.STRING)
    private Status status;

    public Task() {
    }

}
