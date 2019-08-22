package com.project.chess.model;

import lombok.AllArgsConstructor;
import lombok.experimental.Wither;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Wither
@AllArgsConstructor
@Entity
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @NotNull
    private Users playerOne;

    @ManyToOne
    @NotNull
    private Users playerTwo;

    private Status status;
    private Date startedDate;
    private Color playerOneColor;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private State lastState;

    public Game() {
    }

    public Game(Users playerOne, Users playerTwo, Status status, Date startedDate, Color playerOneColor, State lastState ) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.status = status;
        this.startedDate = startedDate;
        this.playerOneColor = playerOneColor;
        this.lastState=lastState;
    }

    public Game(Users playerOne, Users playerTwo, Status status, Date startedDate, Color playerOneColor ) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.status = status;
        this.startedDate = startedDate;
        this.playerOneColor = playerOneColor;
    }

    public Game(Users playerOne, Users playerTwo, Status status, Color playerOneColor) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.status = status;
        this.playerOneColor = playerOneColor;
    }

    public State getLastState() {
        return lastState;
    }

    public void setLastState(State lastState) {
        this.lastState = lastState;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Users getPlayerOne() {
        return playerOne;
    }

    public void setPlayerOne(Users playerOne) {
        this.playerOne = playerOne;
    }

    public Users getPlayerTwo() {
        return playerTwo;
    }

    public void setPlayerTwo(Users playerTwo) {
        this.playerTwo = playerTwo;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Date getStartedDate() {
        return startedDate;
    }

    public void setStartedDate(Date startedDate) {
        this.startedDate = startedDate;
    }

    public Color getPlayerOneColor() {
        return playerOneColor;
    }

    public void setPlayerOneColor(Color playerOneColor) {
        this.playerOneColor = playerOneColor;
    }
}
