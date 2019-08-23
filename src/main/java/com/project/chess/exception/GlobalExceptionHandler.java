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

    @ExceptionHandler(UserNameAlreadyExistsException.class)
    public ResponseEntity handleUserNameAlreadyExistsException(UserNameAlreadyExistsException e) {
        e.setStatus(HttpStatus.BAD_REQUEST.value());
        e.setError(HttpStatus.BAD_REQUEST.name());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(gson.toJson(e));
    }

    @ExceptionHandler(DisplayNameAlreadyExistsException.class)
    public ResponseEntity handleDisplayNameAlreadyExistsException(DisplayNameAlreadyExistsException e) {
        e.setStatus(HttpStatus.BAD_REQUEST.value());
        e.setError(HttpStatus.BAD_REQUEST.name());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(gson.toJson(e));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity handleUserNotFoundException(UserNotFoundException e) {
        e.setStatus(HttpStatus.NOT_FOUND.value());
        e.setError(HttpStatus.NOT_FOUND.name());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(gson.toJson(e));
    }

    @ExceptionHandler(GameNotFoundException.class)
    public ResponseEntity handleGameNotFoundException(GameNotFoundException e) {
        e.setStatus(HttpStatus.NOT_FOUND.value());
        e.setError(HttpStatus.NOT_FOUND.name());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(gson.toJson(e));
    }
}
