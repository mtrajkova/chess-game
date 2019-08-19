package com.project.chess.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;

    @OneToMany(mappedBy = "playerOne")
    private List<Game> gamesPlayerOne;

    @OneToMany(mappedBy = "playerTwo")
    private List<Game> gamesPlayerTwo;

    public Users() {
    }

    public Users(String username) {
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
