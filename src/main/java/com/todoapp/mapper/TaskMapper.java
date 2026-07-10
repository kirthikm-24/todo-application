package com.todoapp.mapper;

import com.todoapp.dto.CreateTaskRequest;
import com.todoapp.dto.TaskResponse;
import com.todoapp.dto.UpdateTaskRequest;
import com.todoapp.model.Task;

import static com.todoapp.util.TaskUtil.calculateVibe;

public class TaskMapper {
    public static Task toEntity(CreateTaskRequest d) {
        Task t = new Task();
        t.setName(d.getName());
        t.setDescription(d.getDescription());
        t.setTentativeStartDate(d.getTentativeStartDate());
        t.setTentativeEndDate(d.getTentativeEndDate());
        t.setActualStartDate(d.getActualStartDate());
        t.setActualEndDate(d.getActualEndDate());
        t.setStatus(d.getStatus());
        return t;
    }

    public static void applyUpdate(UpdateTaskRequest d, Task existing) {
        existing.setName(d.getName());
        existing.setDescription(d.getDescription());
        existing.setTentativeStartDate(d.getTentativeStartDate());
        existing.setTentativeEndDate(d.getTentativeEndDate());
        existing.setActualStartDate(d.getActualStartDate());
        existing.setActualEndDate(d.getActualEndDate());
        existing.setStatus(d.getStatus());
    }

    public static TaskResponse toResponse(Task t) {
        TaskResponse r = new TaskResponse();
        r.setId(t.getId());
        r.setName(t.getName());
        r.setDescription(t.getDescription());
        r.setTentativeEndDate(t.getTentativeEndDate());
        r.setActualEndDate(t.getActualEndDate());
        r.setVibe(calculateVibe(t));
        r.setStatus(t.getStatus());
        return r;
    }
}
