package com.todo.todo.controller;

import com.todo.todo.service.GreetingService;
import org.springframework.web.bind.annotation.*;

@RestController
public class HelloController {

    private final GreetingService greetingService;

    public HelloController(GreetingService greetingService) {
        this.greetingService=greetingService;
    }

    @GetMapping("/hello")
    public String hello() {
        return greetingService.getMessage();
    }
}