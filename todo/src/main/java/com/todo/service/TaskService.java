package com.todo.service;

import com.todo.model.Task;
import com.todo.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository){
        this.taskRepository=taskRepository;
    }

    public List<Task> getAllTasks() {
        List<Task> tasks=taskRepository.findAll();
        for(Task task:tasks){
            String vibe=calculateVibe(task);
            task.setVibe(vibe);
        }
        return tasks;
    }

    public Task getTaskById (Long id) {
        Task task = taskRepository.findById(id).orElse(null);
        if(task==null) return null;
        String vibe=calculateVibe(task);
        task.setVibe(vibe);
        return task;
    }

    public Task addTask(Task task) {
        return taskRepository.save(task);
    }

    public Task updateTask(Long id, Task updatedTask) {
        updatedTask.setId(id);
        return taskRepository.save(updatedTask);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    public void deleteAllTasks (){
        taskRepository.deleteAll();
    }

    public String calculateVibe(Task task){
        if(task.getActualEndDate()==null) return "PENDING";
        long days= ChronoUnit.DAYS.between(task.getTentativeEndDate(),task.getActualEndDate());
        if(days==0) return "DONE ON TIME";
        if(days<0) return "DONE BEFORE TIME";
        return "DELAYED BY " + days + " days";
    }
}
