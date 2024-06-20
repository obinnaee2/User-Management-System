package com.Payaza.user_api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class globalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("errors", errors);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResponseStatusException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Map<String, Object>> handleResponseStatusException(ResponseStatusException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.NOT_FOUND.value());
        response.put("error", ex.getReason());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleOtherExceptions(Exception ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.NOT_FOUND.value());
        response.put("error", "An unexpected error occurred: " + ex.getMessage());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
