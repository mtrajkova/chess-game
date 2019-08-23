package com.project.chess.model.dto;

import com.project.chess.model.Game;
import com.project.chess.model.Status;

import java.util.Date;

public class MyGameDto {
    private Long id;
    private String opponentName;
    private Status status;
    private Date dateStarted;
    private String state;
    private String PGN;

    public MyGameDto() {
    }

    public MyGameDto(Long id, String opponentName, Status status, Date dateStarted, String state, String PGN) {
        this.id = id;
        this.opponentName = opponentName;
        this.status = status;
        this.dateStarted = dateStarted;
        this.state = state;
        this.PGN = PGN;
    }

    public MyGameDto(Long id, String opponentName, Status status, Date dateStarted) {
        this.id = id;
        this.opponentName = opponentName;
        this.status = status;
        this.dateStarted = dateStarted;
    }

    public static MyGameDto fromGame(Game game, Long userId) {
        if (game.getInviter().equals(userId)) {
            return new MyGameDto(game.getId(), game.getPlayerTwo().getDisplayName(), game.getStatus(), game.getStartedDate(), game.getLastState().getFEN(), game.getPGN());
        }
        return new MyGameDto(game.getId(), game.getPlayerOne().getDisplayName(), game.getStatus(), game.getStartedDate(), game.getLastState().getFEN(), game.getPGN());
    }

    public String getPGN() {
        return PGN;
    }

    public void setPGN(String PGN) {
        this.PGN = PGN;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setOpponentName(String opponentName) {
        this.opponentName = opponentName;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setDateStarted(Date dateStarted) {
        this.dateStarted = dateStarted;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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
