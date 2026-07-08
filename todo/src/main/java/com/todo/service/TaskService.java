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
            task.setVibe(calculateVibe(task));
        }
        return tasks;
    }

    public Task getTaskById (Long id) {
        Task task = taskRepository.findById(id).orElse(null);
        if(task==null) return null;
        task.setVibe(calculateVibe(task));
        return task;
    }

    public Task addTask(Task task) {
        task.setVibe(calculateVibe(task));
        return taskRepository.save(task);
    }

    public Task updateTask(Long id, Task updatedTask) {
        Task existing = taskRepository.findById(id).orElseThrow(()-> new RuntimeException("Task not found"));
        existing.setName(updatedTask.getName());
        existing.setDescription(updatedTask.getDescription());
        existing.setTentativeStartDate(updatedTask.getTentativeStartDate());
        existing.setTentativeEndDate(updatedTask.getTentativeEndDate());
        existing.setActualStartDate(updatedTask.getActualStartDate());
        existing.setActualEndDate(updatedTask.getActualEndDate());
        existing.setStatus(updatedTask.getStatus());
        existing.setVibe(calculateVibe(existing));
        return taskRepository.save(existing);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    public void deleteAllTasks (){
        taskRepository.deleteAll();
    }

    public String calculateVibe(Task task){
        if(task.getActualEndDate()==null) return "PENDING";
        if(task.getTentativeEndDate()==null) return "DONE (No tentative date";
        long days= ChronoUnit.DAYS.between(task.getTentativeEndDate(),task.getActualEndDate());
        if(days==0) return "DONE ON TIME";
        if(days<0) return "DONE BEFORE TIME";
        return "DELAYED BY " + days + " days";
    }
}
