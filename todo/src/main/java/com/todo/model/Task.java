package com.todo.model;

import jakarta.persistence.*;
import java.time.LocalDate;

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

    @Transient
    private String vibe;

    public Task(){}

    public Task(Long id, String name) {
        this.id=id;
        this.name=name;
    }

    public Long getId(){
        return id;
    }
    public void setId(Long id) {
        this.id=id;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name=name;
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
    public void setTentativeStartDate(LocalDate tentativeStartDate){
        this.tentativeStartDate=tentativeStartDate;
    }
    public LocalDate getTentativeEndDate() {
        return tentativeEndDate;
    }
    public void setTentativeEndDate(LocalDate tentativeEndDate){
        this.tentativeEndDate=tentativeEndDate;
    }
    public LocalDate getActualStartDate() {
        return actualStartDate;
    }
    public void setActualStartDate(LocalDate actualStartDate){
        this.actualStartDate=actualStartDate;
    }
    public LocalDate getActualEndDate() {
        return actualEndDate;
    }
    public void setActualEndDate(LocalDate actualEndDate){
        this.actualEndDate=actualEndDate;
    }
    public Status getStatus(){
        return status;
    }
    public void setStatus(Status status){
        this.status=status;
    }
    public String getVibe(){
        return vibe;
    }
    public void setVibe(String vibe){
        this.vibe=vibe;
    }
}
