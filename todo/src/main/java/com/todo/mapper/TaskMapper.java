package com.todo.mapper;

import com.todo.dto.CreateTaskRequest;
import com.todo.dto.TaskResponse;
import com.todo.dto.UpdateTaskRequest;
import com.todo.model.Task;

public class TaskMapper {
    public static Task toEntity(CreateTaskRequest d){
        Task t=new Task();
        t.setName(d.getName());
        t.setDescription(d.getDescription());
        t.setTentativeEndDate(d.getTentativeEndDate());
        return t;
    }
    public static void applyUpdate(UpdateTaskRequest d, Task existing){
        existing.setName(d.getName());
        existing.setDescription(d.getDescription());
        existing.setTentativeEndDate(d.getTentativeEndDate());
        existing.setActualEndDate(d.getActualEndDate());
    }
    public static TaskResponse toResponse(Task t){
        TaskResponse r=new TaskResponse();
        r.setId(t.getId());
        r.setName(t.getName());
        r.setDescription(t.getDescription());
        r.setTentativeEndDate(t.getTentativeEndDate());
        r.setActualEndDate(t.getActualEndDate());
        return r;
    }
}
