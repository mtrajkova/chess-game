package com.project.chess.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.List;

@Entity
public class State {
    @Id
    @Column(name = "id_as_FEN")
    private String FEN;

    @ManyToMany(mappedBy = "states")
    @JsonBackReference
    private List<GameHistory> gameHistory;

    public String getFEN() {
        return FEN;
    }

    public void setFEN(String FEN) {
        this.FEN = FEN;
    }

    public List<GameHistory> getGameHistory() {
        return gameHistory;
    }

    public void setGameHistory(List<GameHistory> gameHistory) {
        this.gameHistory = gameHistory;
    }
}
