package com.todoapp.exception;

import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.lang.reflect.Method;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void handleNotFound_shouldReturn404Response() {
        // Arrange
        NotFoundException exception = new NotFoundException("Task not found");

        // Act
        ResponseEntity<Map<String, String>> response =
                handler.handleNotFound(exception);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        Map<String, String> body = response.getBody();
        assertNotNull(body);
        assertEquals("Task not found", body.get("error"));
    }

    @Test
    void handleBadRequest_shouldReturn400Response() {
        // Arrange
        BadRequestException exception =
                new BadRequestException("Invalid dates");

        // Act
        ResponseEntity<Map<String, String>> response =
                handler.handleBadRequest(exception);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        Map<String, String> body = response.getBody();
        assertNotNull(body);
        assertEquals("Invalid dates", body.get("error"));
    }

    @Test
    void handleValidation_shouldReturnValidationErrors() throws NoSuchMethodException {
        // Arrange
        BindingResult bindingResult =
                new BeanPropertyBindingResult(new Object(), "request");

        bindingResult.addError(
                new FieldError(
                        "request",
                        "name",
                        "Name is required"
                )
        );

        Method method =
                DummyController.class.getMethod("create", Object.class);

        MethodParameter methodParameter =
                new MethodParameter(method, 0);

        MethodArgumentNotValidException exception =
                new MethodArgumentNotValidException(methodParameter, bindingResult);

        // Act
        ResponseEntity<Map<String, String>> response =
                handler.handleValidation(exception);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        Map<String, String> body = response.getBody();
        assertNotNull(body);
        assertEquals("Name is required", body.get("name"));
    }

    private static class DummyController {
        public void create(Object request) {
        }
    }
}