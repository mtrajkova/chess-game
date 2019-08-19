package com.project.chess.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private User playerOne;
    private User playerTwo;
    private String status;
    private Date startedDate;
    private Color playerOneColor;

    public Game() {
    }

    public Game(User playerOne, User playerTwo, String status, Date startedDate, Color playerOneColor) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.status = status;
        this.startedDate = startedDate;
        this.playerOneColor = playerOneColor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getPlayerOne() {
        return playerOne;
    }

    public void setPlayerOne(User playerOne) {
        this.playerOne = playerOne;
    }

    public User getPlayerTwo() {
        return playerTwo;
    }

    public void setPlayerTwo(User playerTwo) {
        this.playerTwo = playerTwo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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
