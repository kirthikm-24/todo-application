package com.todo.dto;

import com.todo.model.Status;

import java.time.LocalDate;

public class TaskResponse {
    private Long id;
    private String name;
    private String description;
    private LocalDate tentativeEndDate;
    private LocalDate actualEndDate;
    private String vibe;

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

    public LocalDate getTentativeEndDate() {
        return tentativeEndDate;
    }
    public void setTentativeEndDate(LocalDate tentativeEndDate){
        this.tentativeEndDate=tentativeEndDate;
    }

    public LocalDate getActualEndDate() {
        return actualEndDate;
    }
    public void setActualEndDate(LocalDate actualEndDate){
        this.actualEndDate=actualEndDate;
    }

}
