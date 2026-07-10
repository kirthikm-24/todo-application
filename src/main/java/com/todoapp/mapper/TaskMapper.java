package com.todoapp.mapper;

import com.todoapp.dto.CreateTaskRequest;
import com.todoapp.dto.TaskResponse;
import com.todoapp.dto.UpdateTaskRequest;
import com.todoapp.model.Task;

public class TaskMapper {
    public static Task toEntity(CreateTaskRequest d){
        Task t=new Task();
        t.setName(d.getName());
        t.setDescription(d.getDescription());
        t.setTentativeEndDate(d.getTentativeEndDate());
        t.setStatus(d.getStatus());
        return t;
    }
    public static void applyUpdate(UpdateTaskRequest d, Task existing){
        existing.setName(d.getName());
        existing.setDescription(d.getDescription());
        existing.setTentativeEndDate(d.getTentativeEndDate());
        existing.setActualEndDate(d.getActualEndDate());
        existing.setStatus(d.getStatus());
    }
    public static TaskResponse toResponse(Task t){
        TaskResponse r=new TaskResponse();
        r.setId(t.getId());
        r.setName(t.getName());
        r.setDescription(t.getDescription());
        r.setTentativeEndDate(t.getTentativeEndDate());
        r.setActualEndDate(t.getActualEndDate());
        r.setVibe(t.getVibe());
        r.setStatus(t.getStatus());
        return r;
    }
}
