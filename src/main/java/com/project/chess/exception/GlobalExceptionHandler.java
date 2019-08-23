package com.project.chess.exception;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class GlobalExceptionHandler {
    private Gson gson;

    public GlobalExceptionHandler() {
        gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<?> handleUserAlreadyExistsException(UserAlreadyExistsException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public String handleUserNotFoundException(UserNotFoundException e) {
        e.setStatus(HttpStatus.NOT_FOUND.value());
        e.setError(HttpStatus.NOT_FOUND.name());
        return gson.toJson(e);
    }

    @ExceptionHandler(GameNotFoundException.class)
    public String handleGameNotFoundException(GameNotFoundException e) {
        e.setStatus(HttpStatus.NOT_FOUND.value());
        e.setError(HttpStatus.NOT_FOUND.name());
        return gson.toJson(e);
    }
}
