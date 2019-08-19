package com.project.chess.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class GameHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private Game game;

    private List<String> statesAsFEN;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public List<String> getStatesAsFEN() {
        return statesAsFEN;
    }

    public void setStatesAsFEN(List<String> statesAsFEN) {
        this.statesAsFEN = statesAsFEN;
    }
}
