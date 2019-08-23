package com.project.chess.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class State {
    @Id
    @GeneratedValue
    Long id;

    private String FEN;

    public State() {
    }

    public State(String FEN) {

        this.FEN = FEN;
    }

    // only for test
    public State(State original) {
        this.id = original.id;
        this.FEN = original.FEN;
    }

    public String getFEN() {
        return FEN;
    }

    public void setFEN(String FEN) {
        this.FEN = FEN;
    }

}
