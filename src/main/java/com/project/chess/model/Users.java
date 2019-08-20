package com.project.chess.model;

import lombok.AllArgsConstructor;
import lombok.experimental.Wither;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.List;

@Wither
@AllArgsConstructor
@Entity
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Email(message = "Email is not valid")
    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String display_name;

    private String password;

    private boolean logged_in;

    @OneToMany(mappedBy = "playerOne")
    private List<Game> gamesPlayerOne;

    @OneToMany(mappedBy = "playerTwo")
    private List<Game> gamesPlayerTwo;

    public Users() {
    }

    public Users(@Email String email, String display_name, String password, boolean status) {
        this.username = email;
        this.display_name = display_name;
        this.password = password;
        this.logged_in = status;
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

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isLogged_in() {
        return logged_in;
    }

    public void setLogged_in(boolean logged_in) {
        this.logged_in = logged_in;
    }
}
