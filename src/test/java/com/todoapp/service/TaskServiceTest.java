package com.todoapp.service;

import com.todoapp.dto.CreateTaskRequest;
import com.todoapp.dto.TaskResponse;
import com.todoapp.dto.UpdateTaskRequest;
import com.todoapp.exception.BadRequestException;
import com.todoapp.exception.NotFoundException;
import com.todoapp.mapper.TaskMapper;
import com.todoapp.model.Status;
import com.todoapp.model.Task;
import com.todoapp.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {
    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private TaskService taskService;

    @Test
    void getTaskById_shouldReturnTaskResponse_whenTaskExists() {

        //Arrange
        Long id = 1L;

        Task task = new Task();
        task.setId(id);
        task.setName("Learn Testing");

        TaskResponse response = new TaskResponse();
        response.setId(id);
        response.setName("Learn Testing");

        when(taskRepository.findByIdAndDeletedFalse(id)).thenReturn(Optional.of(task));
        when(taskMapper.toResponse(task)).thenReturn(response);

        //Act
        TaskResponse result = taskService.getTaskById(id);

        //Assert
        assertEquals(response, result);

        verify(taskRepository).findByIdAndDeletedFalse(id);
        verify(taskMapper).toResponse(task);
    }

    @Test
    void getTaskById_shouldThrowNotFoundException_whenTaskDoesNotExist() {

        //Arrange
        Long id = 1L;

        when(taskRepository.findByIdAndDeletedFalse(id))
                .thenReturn(Optional.empty());

        //Act & Assert
        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> taskService.getTaskById(id)
        );

        assertEquals("Task not found with id: " + id, exception.getMessage());

        verify(taskRepository).findByIdAndDeletedFalse(id);
        verifyNoInteractions(taskMapper);

    }

    @Test
    void getAllTasks_shouldReturnTaskResponses_whenTasksExist() {
        // Arrange
        Task task1 = new Task();
        task1.setId(1L);
        task1.setName("Learn Java");

        Task task2 = new Task();
        task2.setId(2L);
        task2.setName("Learn Spring");

        TaskResponse response1 = new TaskResponse();
        response1.setId(1L);
        response1.setName("Learn Java");

        TaskResponse response2 = new TaskResponse();
        response2.setId(2L);
        response2.setName("Learn Spring");

        when(taskRepository.findByDeletedFalse())
                .thenReturn(List.of(task1, task2));

        when(taskMapper.toResponse(task1))
                .thenReturn(response1);

        when(taskMapper.toResponse(task2))
                .thenReturn(response2);

        // Act
        List<TaskResponse> result = taskService.getAllTasks();

        // Assert
        assertEquals(List.of(response1, response2), result);

        verify(taskRepository).findByDeletedFalse();
        verify(taskMapper).toResponse(task1);
        verify(taskMapper).toResponse(task2);
    }

    @Test
    void deleteTask_shouldSoftDeleteTask_whenTaskExists() {
        // Arrange
        Long id = 1L;

        Task task = new Task();
        task.setId(id);
        task.setDeleted(false);

        when(taskRepository.findByIdAndDeletedFalse(id))
                .thenReturn(Optional.of(task));

        ArgumentCaptor<Task> taskCaptor = ArgumentCaptor.forClass(Task.class);

        // Act
        taskService.deleteTask(id);

        // Assert
        verify(taskRepository).findByIdAndDeletedFalse(id);
        verify(taskRepository).save(taskCaptor.capture());
        verifyNoInteractions(taskMapper);

        Task savedTask = taskCaptor.getValue();
        assertTrue(savedTask.isDeleted());
    }

    @Test
    void deleteTask_shouldThrowNotFoundException_whenTaskDoesNotExist() {
        // Arrange
        Long id = 1L;

        when(taskRepository.findByIdAndDeletedFalse(id))
                .thenReturn(Optional.empty());

        // Act + Assert
        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> taskService.deleteTask(id)
        );

        assertEquals("Task not found with id: " + id, exception.getMessage());

        verify(taskRepository).findByIdAndDeletedFalse(id);
        verify(taskRepository, never()).save(any(Task.class));
        verifyNoInteractions(taskMapper);
    }

    @Test
    void deleteAllTasks_shouldSoftDeleteAllTasks_whenTasksExist() {
        // Arrange
        Task task1 = new Task();
        task1.setDeleted(false);

        Task task2 = new Task();
        task2.setDeleted(false);

        when(taskRepository.findByDeletedFalse())
                .thenReturn(List.of(task1, task2));

        // Act
        taskService.deleteAllTasks();

        // Assert
        verify(taskRepository).findByDeletedFalse();
        verify(taskRepository).saveAll(List.of(task1, task2));
        verifyNoInteractions(taskMapper);

        assertTrue(task1.isDeleted());
        assertTrue(task2.isDeleted());
    }

    @Test
    void addTask_shouldCreateTask_whenRequestIsValid() {
        // Arrange
        CreateTaskRequest request = new CreateTaskRequest();
        request.setTentativeStartDate(LocalDate.now());
        request.setTentativeEndDate(LocalDate.now().plusDays(2));

        Task task = new Task();
        task.setTentativeStartDate(request.getTentativeStartDate());
        task.setTentativeEndDate(request.getTentativeEndDate());

        TaskResponse response = new TaskResponse();

        when(taskMapper.toEntity(request)).thenReturn(task);
        when(taskMapper.toResponse(task)).thenReturn(response);

        // Act
        TaskResponse result = taskService.addTask(request);

        // Assert
        assertEquals(response, result);

        verify(taskMapper).toEntity(request);
        verify(taskRepository).save(task);
        verify(taskMapper).toResponse(task);

        assertEquals(Status.SCHEDULED, task.getStatus());
    }

    @Test
    void addTask_shouldThrowBadRequestException_whenDatesAreInvalid() {
        // Arrange
        CreateTaskRequest request = new CreateTaskRequest();

        Task task = new Task();
        task.setTentativeStartDate(LocalDate.now().plusDays(2));
        task.setTentativeEndDate(LocalDate.now());

        when(taskMapper.toEntity(request)).thenReturn(task);

        // Act + Assert
        BadRequestException exception = assertThrows(
                BadRequestException.class,
                () -> taskService.addTask(request)
        );

        assertEquals(
                "Tentative start date cannot be after tentative end date.",
                exception.getMessage()
        );

        verify(taskMapper).toEntity(request);
        verify(taskRepository, never()).save(any(Task.class));
        verify(taskMapper, never()).toResponse(any(Task.class));
    }

    @Test
    void updateTask_shouldUpdateTask_whenTaskExists() {
        // Arrange
        Long id = 1L;

        UpdateTaskRequest request = new UpdateTaskRequest();

        Task existing = new Task();
        existing.setTentativeStartDate(LocalDate.now());
        existing.setTentativeEndDate(LocalDate.now().plusDays(2));

        TaskResponse response = new TaskResponse();

        when(taskRepository.findByIdAndDeletedFalse(id))
                .thenReturn(Optional.of(existing));

        when(taskMapper.toResponse(existing))
                .thenReturn(response);

        // Act
        TaskResponse result = taskService.updateTask(id, request);

        // Assert
        assertEquals(response, result);

        verify(taskRepository).findByIdAndDeletedFalse(id);
        verify(taskMapper).applyUpdate(request, existing);
        verify(taskRepository).save(existing);
        verify(taskMapper).toResponse(existing);

        assertEquals(Status.SCHEDULED, existing.getStatus());
    }

    @Test
    void updateTask_shouldThrowNotFoundException_whenTaskDoesNotExist() {
        // Arrange
        Long id = 1L;

        UpdateTaskRequest request = new UpdateTaskRequest();

        when(taskRepository.findByIdAndDeletedFalse(id))
                .thenReturn(Optional.empty());

        // Act + Assert
        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> taskService.updateTask(id, request)
        );

        assertEquals("Task not found with id: " + id, exception.getMessage());

        // Verify
        verify(taskRepository).findByIdAndDeletedFalse(id);
        verifyNoInteractions(taskMapper);
        verify(taskRepository, never()).save(any(Task.class));
    }

    @Test
    void updateTask_shouldThrowBadRequestException_whenDatesAreInvalid() {
        // Arrange
        Long id = 1L;

        UpdateTaskRequest request = new UpdateTaskRequest();

        Task existing = new Task();
        existing.setTentativeStartDate(LocalDate.now().plusDays(2));
        existing.setTentativeEndDate(LocalDate.now());

        when(taskRepository.findByIdAndDeletedFalse(id))
                .thenReturn(Optional.of(existing));

        // Act + Assert
        BadRequestException exception = assertThrows(
                BadRequestException.class,
                () -> taskService.updateTask(id, request)
        );

        assertEquals(
                "Tentative start date cannot be after tentative end date.",
                exception.getMessage()
        );

        // Verify
        verify(taskRepository).findByIdAndDeletedFalse(id);
        verify(taskMapper).applyUpdate(request, existing);
        verify(taskRepository, never()).save(any(Task.class));
        verify(taskMapper, never()).toResponse(any(Task.class));
    }
}
