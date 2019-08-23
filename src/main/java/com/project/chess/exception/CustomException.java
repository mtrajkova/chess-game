package com.project.chess.exception;

import com.google.gson.annotations.Expose;

public class CustomException extends RuntimeException{
    @Expose
    private String message = null;
    @Expose
    private String error;
    @Expose
    private Integer status;

    public CustomException(String message) {
        this.message = message;
    }

    public CustomException(){}

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
