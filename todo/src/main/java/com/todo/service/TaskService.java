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

    public Task getTaskById (Long id) {
        return tasks.stream()
                .filter(task -> task.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public Task addTask(Task task) {
        task.setId(nextId);
        nextId++;
        tasks.add(task);
        return task;
    }

    public Task updateTask(Long id, Task updatedTask) {
        for(int i=0;i<tasks.size();i++) {
            if(tasks.get(i).getId().equals(id)) {
                tasks.set(i,updatedTask);
                return updatedTask;
            }
        }
        return null;
    }

    public void deleteTask(Long id) {
        tasks.removeIf(task->task.getId().equals(id));
    }

    public void deleteAllTasks (){
        tasks.clear();
    }
}
