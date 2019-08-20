package com.project.chess.model.dto;

import com.project.chess.model.Status;

import java.util.Date;

public class MyGameDto {
    private String opponentName;
    private Status status;
    private Date dateStarted;

    public String getOpponentName() {
        return opponentName;
    }

    public Status getStatus() {
        return status;
    }

    public Date getDateStarted() {
        return dateStarted;
    }
}
