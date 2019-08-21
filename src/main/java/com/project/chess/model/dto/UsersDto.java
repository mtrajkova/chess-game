package com.project.chess.model.dto;

import com.project.chess.model.Users;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class UsersDto {

    private String username;
    private String password;
    private String displayName;

    public UsersDto(Users user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.displayName = user.getDisplayName();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
