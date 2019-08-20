package com.project.chess.model;

import lombok.AllArgsConstructor;
import lombok.experimental.Wither;

import javax.persistence.*;
import java.util.Date;

@Wither
@AllArgsConstructor
@Entity
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Users playerOne;
    @ManyToOne
    private Users playerTwo;
    private String status;
    private Date startedDate;
    private Color playerOneColor;

    public Game() {
    }

    public Game(Users playerOne, Users playerTwo, String status, Date startedDate, Color playerOneColor) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.status = status;
        this.startedDate = startedDate;
        this.playerOneColor = playerOneColor;
    }

    public Game(Users playerOne, Users playerTwo, String status, Color playerOneColor) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.status = status;
        this.playerOneColor = playerOneColor;
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