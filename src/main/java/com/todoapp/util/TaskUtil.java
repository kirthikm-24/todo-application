package com.todoapp.util;

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
}
