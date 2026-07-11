package com.todoapp.mapper;

import com.todoapp.dto.CreateTaskRequest;
import com.todoapp.dto.TaskResponse;
import com.todoapp.dto.UpdateTaskRequest;
import com.todoapp.model.Task;
import com.todoapp.util.TaskUtil;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", imports = TaskUtil.class)
public interface TaskMapper {
    Task toEntity(CreateTaskRequest request);

    void applyUpdate(UpdateTaskRequest request, @MappingTarget Task task);

    @Mapping(target = "vibe", expression = "java(TaskUtil.calculateVibe(task))")
    TaskResponse toResponse(Task task);
}
