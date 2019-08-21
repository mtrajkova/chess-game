package com.project.chess.model.dto;

import com.project.chess.model.Users;

public class ActiveUserDto {

    private String username;
    private String displayName;
    private boolean isLoggedIn;


    public String getUsername() { return  username; }

    public String getDisplayName() {
        return displayName;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public ActiveUserDto(String username, String displayName, boolean isLoggedIn) {
        this.username = username;
        this.displayName = displayName;
        this.isLoggedIn = isLoggedIn;
    }

    public static ActiveUserDto fromUsers(Users user) {
        return new ActiveUserDto(user.getUsername(), user.getDisplayName(), user.isLoggedIn());
    }
}
