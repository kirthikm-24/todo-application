package com.todoapp.util;

import com.todoapp.model.Task;

import java.time.temporal.ChronoUnit;

public class TaskUtil {
    public static String calculateVibe(Task task) {
        if (task.getActualEndDate() == null) return "PENDING";
        if (task.getTentativeEndDate() == null) return "DONE (No tentative date)";
        long days = ChronoUnit.DAYS.between(task.getTentativeEndDate(), task.getActualEndDate());
        if (days == 0) return "DONE ON TIME";
        if (days < 0) return "DONE BEFORE TIME";
        return "DELAYED BY " + days + " days";
    }
}
