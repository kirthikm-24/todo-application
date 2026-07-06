package com.todo.service;

import com.todo.model.Task;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {
    private List<Task> tasks = new ArrayList<>();

    private Long nextId=1L;

    public List<Task> getAllTasks() {
        return tasks;
    }

    public Task addTask(Task task) {
        task.setId(nextId);
        nextId++;
        tasks.add(task);
        return task;
    }
}
