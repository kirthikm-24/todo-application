package com.todoapp.util;

import com.todoapp.exception.BadRequestException;
import com.todoapp.model.Status;
import com.todoapp.model.Task;
import lombok.experimental.UtilityClass;

import java.time.temporal.ChronoUnit;

@UtilityClass
public class TaskUtil {
    public static String calculateVibe(Task task) {
        if (task.getActualEndDate() == null) return "PENDING";
        long days = ChronoUnit.DAYS.between(task.getTentativeEndDate(), task.getActualEndDate());
        if (days == 0) return "DONE ON TIME";
        if (days < 0) return "DONE BEFORE TIME";
        return "DELAYED BY " + days + " days";
    }

    public static Status calculateStatus(Task task) {
        if (task.getActualEndDate() != null) {
            return Status.COMPLETED;
        }
        if (task.getActualStartDate() != null) {
            return Status.IN_PROGRESS;
        }
        return Status.SCHEDULED;
    }

    public static void validate(Task task) {
        if (task.getTentativeStartDate().isAfter(task.getTentativeEndDate())) {
            throw new BadRequestException(
                    "Tentative start date cannot be after tentative end date.");
        }

        if (task.getActualEndDate() != null &&
                task.getActualStartDate() == null) {
            throw new BadRequestException(
                    "Actual end date cannot be set without an actual start date.");
        }

        if (task.getActualStartDate() != null &&
                task.getActualEndDate() != null &&
                task.getActualStartDate().isAfter(task.getActualEndDate())) {
            throw new BadRequestException(
                    "Actual start date cannot be after actual end date.");
        }
    }
}
