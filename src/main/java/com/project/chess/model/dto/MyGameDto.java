package com.project.chess.model.dto;

import com.project.chess.model.Status;

import java.util.Date;

public class MyGameDto {
    private Long id;
    private String opponentName;
    private Status status;
    private Date dateStarted;
    private String state;

    public MyGameDto() {
    }

    public MyGameDto(Long id, String opponentName, Status status, Date dateStarted, String state) {
        this.id = id;
        this.opponentName = opponentName;
        this.status = status;
        this.dateStarted = dateStarted;
        this.state=state;
    }

    public MyGameDto(Long id, String opponentName, Status status, Date dateStarted) {
        this.id = id;
        this.opponentName = opponentName;
        this.status = status;
        this.dateStarted = dateStarted;
    }

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
