package com.project.chess.exception;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class GlobalExceptionHandler {
    private Gson gson;

    public GlobalExceptionHandler() {
        gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
    }

    @ExceptionHandler(UserNameAlreadyExistsException.class)
    public String handleUserNameAlreadyExistsException(UserNameAlreadyExistsException e) {
        e.setStatus(HttpStatus.BAD_REQUEST.value());
        e.setError(HttpStatus.BAD_REQUEST.name());
        return gson.toJson(e);
    }

    @ExceptionHandler(DisplayNameAlreadyExistsException.class)
    public String handleDisplayNameAlreadyExistsException(DisplayNameAlreadyExistsException e) {
        e.setStatus(HttpStatus.BAD_REQUEST.value());
        e.setError(HttpStatus.BAD_REQUEST.name());
        return gson.toJson(e);
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
