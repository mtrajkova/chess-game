package com.project.chess.model;

import lombok.AllArgsConstructor;
import lombok.experimental.Wither;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
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
    @NotEmpty(message = "Email can not be empty")
    private String username;

    @Column(unique = true)
    @NotEmpty(message = "Display name can not be empty")
    private String displayName;

    @NotEmpty(message = "Password can not be empty")
    private String password;

    private boolean loggedIn;

    @OneToMany(mappedBy = "playerOne")
    private List<Game> gamesPlayerOne;

    @OneToMany(mappedBy = "playerTwo")
    private List<Game> gamesPlayerTwo;

    public Users() {
    }

    public Users(@Email String email, String display_name, String password, boolean status) {
        this.username = email;
        this.displayName = display_name;
        this.password = password;
        this.loggedIn = status;
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

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }
}
