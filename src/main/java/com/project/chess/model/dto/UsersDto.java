package com.project.chess.model.dto;

import com.project.chess.model.Users;
import com.project.chess.validation.PasswordMatches;
import com.project.chess.validation.ValidPassword;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@NoArgsConstructor
@PasswordMatches
public class UsersDto {

    @Email(message = "Email is not valid")
    @NotEmpty(message = "Email can not be empty")
    private String username;

    @NotEmpty(message = "Password can not be empty")
    @ValidPassword
    private String password;

    @NotEmpty(message = "Display name can not be empty")
    private String displayName;

    private String matchingPassword;

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }

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
