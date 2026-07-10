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

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<TaskResponse> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();

        return tasks.stream()
                .map(TaskMapper::toResponse)
                .toList();
    }

    public TaskResponse getTaskById(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new NotFoundException("Task not found with id: " + id));
        return TaskMapper.toResponse(task);
    }

    public TaskResponse addTask(CreateTaskRequest request) {
        Task task = TaskMapper.toEntity(request);
        TaskUtil.validate(task);
        task.setStatus(TaskUtil.calculateStatus(task));
        taskRepository.save(task);
        return TaskMapper.toResponse(task);
    }

    public TaskResponse updateTask(Long id, UpdateTaskRequest updatedTaskRequest) {
        Task existing = taskRepository.findById(id).orElseThrow(() -> new NotFoundException("Task not found with id: " + id));
        TaskMapper.applyUpdate(updatedTaskRequest, existing);
        TaskUtil.validate(existing);
        existing.setStatus(TaskUtil.calculateStatus(existing));
        taskRepository.save(existing);
        return TaskMapper.toResponse(existing);
    }

    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new NotFoundException("Task not found with id: " + id);
        }
        taskRepository.deleteById(id);
    }

    public void deleteAllTasks() {
        taskRepository.deleteAll();
    }

}
