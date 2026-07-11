package com.todoapp.repository;

import com.todoapp.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByDeletedFalse();

    Optional<Task> findByIdAndDeletedFalse(Long id);
}
