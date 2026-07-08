package com.todo.controller;

import com.todo.dto.*;
import com.todo.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/todos")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService){
        this.taskService=taskService;
    }

    @GetMapping
    public List<TaskResponse> getTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/{id}")
    public TaskResponse getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id);
    }

    @PostMapping
    public TaskResponse createTask(@Valid @RequestBody CreateTaskRequest request){
        return taskService.addTask(request);
    }

    @PutMapping("/{id}")
    public TaskResponse updateTask(@PathVariable Long id,@Valid @RequestBody UpdateTaskRequest task) {
        return taskService.updateTask(id,task);
    }

    @DeleteMapping ("/{id}")
    public String deleteTask(@PathVariable Long id){
        taskService.deleteTask(id);
        return "Task deleted";
    }

    @DeleteMapping
    public String deleteAllTasks (){
        taskService.deleteAllTasks();
        return "All tasks deleted";
    }
}