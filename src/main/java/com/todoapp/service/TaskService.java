package com.todoapp.service;

import com.todoapp.dto.CreateTaskRequest;
import com.todoapp.dto.TaskResponse;
import com.todoapp.dto.UpdateTaskRequest;
import com.todoapp.exception.NotFoundException;
import com.todoapp.mapper.TaskMapper;
import com.todoapp.model.Task;
import com.todoapp.repository.TaskRepository;
import com.todoapp.util.TaskUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public TaskService(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    public List<TaskResponse> getAllTasks() {
        return taskRepository.findByDeletedFalse()
                .stream()
                .map(taskMapper::toResponse)
                .toList();
    }

    public TaskResponse getTaskById(Long id) {
        Task task = taskRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new NotFoundException("Task not found with id: " + id));
        return taskMapper.toResponse(task);
    }

    public TaskResponse addTask(CreateTaskRequest request) {
        Task task = taskMapper.toEntity(request);
        TaskUtil.validate(task);
        task.setStatus(TaskUtil.calculateStatus(task));
        taskRepository.save(task);
        return taskMapper.toResponse(task);
    }

    public TaskResponse updateTask(Long id, UpdateTaskRequest updatedTaskRequest) {
        Task existing = taskRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new NotFoundException("Task not found with id: " + id));
        taskMapper.applyUpdate(updatedTaskRequest, existing);
        TaskUtil.validate(existing);
        existing.setStatus(TaskUtil.calculateStatus(existing));
        taskRepository.save(existing);
        return taskMapper.toResponse(existing);
    }

    public void deleteTask(Long id) {
        Task task = taskRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new NotFoundException("Task not found with id: " + id));
        task.setDeleted(true);
        taskRepository.save(task);
    }

    public void deleteAllTasks() {
        List<Task> tasks = taskRepository.findByDeletedFalse();
        tasks.forEach(task -> task.setDeleted(true));
        taskRepository.saveAll(tasks);
    }

}
