package com.project.chess.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "game_history")
public class GameHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @NotNull
    private Game game;

    @ManyToMany
    @JoinTable(
            name = "game_state",
            joinColumns = { @JoinColumn(name = "game_history_id") },
            inverseJoinColumns = {@JoinColumn(name = "state_id")}
    )
    private List<State> states;

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

}
