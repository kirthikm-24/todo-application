package com.todoapp.util;

import com.todoapp.exception.BadRequestException;
import com.todoapp.model.Status;
import com.todoapp.model.Task;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TaskUtilTest {

    @Test
    void calculateVibe_shouldReturnPending_whenActualEndDateIsNull() {
        // Arrange
        Task task = new Task();
        task.setActualEndDate(null);

        // Act
        String vibe = TaskUtil.calculateVibe(task);

        // Assert
        assertEquals("PENDING", vibe);
    }

    @Test
    void calculateVibe_shouldReturnDoneOnTime_whenCompletedOnTentativeEndDate() {
        // Arrange
        Task task = new Task();

        LocalDate date = LocalDate.now();
        task.setTentativeEndDate(date);
        task.setActualEndDate(date);

        // Act
        String vibe = TaskUtil.calculateVibe(task);

        // Assert
        assertEquals("DONE ON TIME", vibe);
    }

    @Test
    void calculateVibe_shouldReturnDoneBeforeTime_whenCompletedBeforeTentativeEndDate() {
        // Arrange
        Task task = new Task();

        task.setTentativeEndDate(LocalDate.now());
        task.setActualEndDate(LocalDate.now().minusDays(2));

        // Act
        String vibe = TaskUtil.calculateVibe(task);

        // Assert
        assertEquals("DONE BEFORE TIME", vibe);
    }

    @Test
    void calculateVibe_shouldReturnDelayed_whenCompletedAfterTentativeEndDate() {
        // Arrange
        Task task = new Task();

        task.setTentativeEndDate(LocalDate.now());
        task.setActualEndDate(LocalDate.now().plusDays(3));

        // Act
        String vibe = TaskUtil.calculateVibe(task);

        // Assert
        assertEquals("DELAYED BY 3 days", vibe);
    }

    @Test
    void calculateStatus_shouldReturnCompleted_whenActualEndDateExists() {
        // Arrange
        Task task = new Task();
        task.setActualEndDate(LocalDate.now());

        // Act
        Status status = TaskUtil.calculateStatus(task);

        // Assert
        assertEquals(Status.COMPLETED, status);
    }

    @Test
    void calculateStatus_shouldReturnInProgress_whenActualStartDateExists() {
        // Arrange
        Task task = new Task();
        task.setActualStartDate(LocalDate.now());

        // Act
        Status status = TaskUtil.calculateStatus(task);

        // Assert
        assertEquals(Status.IN_PROGRESS, status);
    }

    @Test
    void calculateStatus_shouldReturnScheduled_whenNoActualDatesExist() {
        // Arrange
        Task task = new Task();

        // Act
        Status status = TaskUtil.calculateStatus(task);

        // Assert
        assertEquals(Status.SCHEDULED, status);
    }

    @Test
    void validate_shouldNotThrowException_whenTaskIsValid() {
        // Arrange
        Task task = new Task();

        task.setTentativeStartDate(LocalDate.now());
        task.setTentativeEndDate(LocalDate.now().plusDays(2));

        // Act + Assert
        assertDoesNotThrow(() -> TaskUtil.validate(task));
    }

    @Test
    void validate_shouldThrowBadRequestException_whenTentativeStartIsAfterTentativeEnd() {
        // Arrange
        Task task = new Task();

        task.setTentativeStartDate(LocalDate.now().plusDays(2));
        task.setTentativeEndDate(LocalDate.now());

        // Act + Assert
        BadRequestException exception = assertThrows(
                BadRequestException.class,
                () -> TaskUtil.validate(task)
        );

        assertEquals(
                "Tentative start date cannot be after tentative end date.",
                exception.getMessage()
        );
    }

    @Test
    void validate_shouldThrowBadRequestException_whenActualEndExistsWithoutActualStart() {
        // Arrange
        Task task = new Task();

        task.setTentativeStartDate(LocalDate.now());
        task.setTentativeEndDate(LocalDate.now().plusDays(2));
        task.setActualEndDate(LocalDate.now());

        // Act + Assert
        BadRequestException exception = assertThrows(
                BadRequestException.class,
                () -> TaskUtil.validate(task)
        );

        assertEquals(
                "Actual end date cannot be set without an actual start date.",
                exception.getMessage()
        );
    }

    @Test
    void validate_shouldThrowBadRequestException_whenActualStartIsAfterActualEnd() {
        // Arrange
        Task task = new Task();

        task.setTentativeStartDate(LocalDate.now());
        task.setTentativeEndDate(LocalDate.now().plusDays(2));
        task.setActualStartDate(LocalDate.now().plusDays(2));
        task.setActualEndDate(LocalDate.now());

        // Act + Assert
        BadRequestException exception = assertThrows(
                BadRequestException.class,
                () -> TaskUtil.validate(task)
        );

        assertEquals(
                "Actual start date cannot be after actual end date.",
                exception.getMessage()
        );
    }

    @Test
    void validate_shouldNotThrowException_whenActualStartExistsWithoutActualEnd() {
        // Arrange
        Task task = new Task();

        task.setTentativeStartDate(LocalDate.now());
        task.setTentativeEndDate(LocalDate.now().plusDays(2));
        task.setActualStartDate(LocalDate.now());

        // Act + Assert
        assertDoesNotThrow(() -> TaskUtil.validate(task));
    }

    @Test
    void validate_shouldNotThrowException_whenNoActualDatesExist() {
        // Arrange
        Task task = new Task();

        task.setTentativeStartDate(LocalDate.now());
        task.setTentativeEndDate(LocalDate.now().plusDays(2));

        // Act + Assert
        assertDoesNotThrow(() -> TaskUtil.validate(task));
    }

    @Test
    void validate_shouldNotThrowException_whenActualStartIsBeforeActualEnd() {
        // Arrange
        Task task = new Task();

        task.setTentativeStartDate(LocalDate.now());
        task.setTentativeEndDate(LocalDate.now().plusDays(5));

        task.setActualStartDate(LocalDate.now());
        task.setActualEndDate(LocalDate.now().plusDays(2));

        // Act + Assert
        assertDoesNotThrow(() -> TaskUtil.validate(task));
    }
}
