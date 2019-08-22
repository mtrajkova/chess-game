package com.project.chess.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class State {
    @Id
    @GeneratedValue
    Long id;

    private String FEN;

    public State(){}

    public State(String FEN) {

        this.FEN = FEN;
    }

    public String getFEN() {
        return FEN;
    }

    public void setFEN(String FEN) {
        this.FEN = FEN;
    }

}
