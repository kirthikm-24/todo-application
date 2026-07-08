package com.todo.controller;

import com.todo.dto.*;
import com.todo.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<TaskResponse> getTaskById(@PathVariable Long id) {

        TaskResponse response = taskService.getTaskById(id);
        if(response==null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@Valid @RequestBody CreateTaskRequest request){
        TaskResponse response = taskService.addTask(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public TaskResponse updateTask(@PathVariable Long id,@Valid @RequestBody UpdateTaskRequest task) {
        return taskService.updateTask(id,task);
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id){
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAllTasks (){
        taskService.deleteAllTasks();
        return ResponseEntity.noContent().build();
    }
}