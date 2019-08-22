package com.project.chess.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.project.chess.model.dto.ActiveUserDto;
import com.project.chess.model.dto.UsersDto;
import lombok.AllArgsConstructor;
import lombok.experimental.Wither;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Wither
@AllArgsConstructor
@Entity
public class Users implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String displayName;

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

    public Users(Users original){
        this.id = original.id;
        this.username = original.username;
        this.password = original.password;
        this.displayName = original.displayName;
        this.loggedIn = original.loggedIn;
        this.gamesPlayerOne = original.gamesPlayerOne;
        this.gamesPlayerTwo = original.gamesPlayerTwo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Users users = (Users) o;
        return loggedIn == users.loggedIn &&
                Objects.equals(id, users.id) &&
                Objects.equals(username, users.username) &&
                Objects.equals(displayName, users.displayName) &&
                Objects.equals(password, users.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, displayName, password, loggedIn);
    }

    public static Users fromUsersDto(UsersDto dto){
        return new Users(dto.getUsername(), dto.getDisplayName(), dto.getPassword(), false);
    }

//    public static Users fromActiveUsersDto(ActiveUserDto activeUserDto) {
//        return new Users(activeUserDto.getUsername(), activeUserDto.getDisplayName(), activeUserDto.isLoggedIn());
//    }

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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
