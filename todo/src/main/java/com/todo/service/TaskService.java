package com.todo.service;

import com.todo.dto.*;
import com.todo.mapper.TaskMapper;
import com.todo.model.Task;
import com.todo.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository){
        this.taskRepository=taskRepository;
    }

    public List<TaskResponse> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();

        for (Task task : tasks) {
            task.setVibe(calculateVibe(task));
        }

        return tasks.stream()
                .map(TaskMapper::toResponse)
                .toList();
    }

    public TaskResponse getTaskById (Long id) {
        Task task = taskRepository.findById(id).orElse(null);
        if(task==null) return null;
        task.setVibe(calculateVibe(task));
        return TaskMapper.toResponse(task);
    }

    public TaskResponse addTask(CreateTaskRequest request) {
        Task task= TaskMapper.toEntity(request);
        task.setVibe(calculateVibe(task));
        taskRepository.save(task);
        return TaskMapper.toResponse(task);
    }

    public TaskResponse updateTask(Long id, UpdateTaskRequest updatedTaskRequest) {
        Task existing = taskRepository.findById(id).orElseThrow(()-> new RuntimeException("Task not found"));
        TaskMapper.applyUpdate(updatedTaskRequest,existing);
        existing.setVibe(calculateVibe(existing));
        taskRepository.save(existing);
        return TaskMapper.toResponse(existing);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    public void deleteAllTasks (){
        taskRepository.deleteAll();
    }

    public String calculateVibe(Task task){
        if(task.getActualEndDate()==null) return "PENDING";
        if(task.getTentativeEndDate()==null) return "DONE (No tentative date)";
        long days= ChronoUnit.DAYS.between(task.getTentativeEndDate(),task.getActualEndDate());
        if(days==0) return "DONE ON TIME";
        if(days<0) return "DONE BEFORE TIME";
        return "DELAYED BY " + days + " days";
    }
}
