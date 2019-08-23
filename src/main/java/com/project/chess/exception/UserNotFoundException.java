package com.project.chess.exception;

public class UserNotFoundException extends CustomException {
    public UserNotFoundException(Long id) {
        super("User with id " + id + " does not exist");
    }

    public UserNotFoundException(String username) {
        super("User with username "  + username + " does not exist");
    }
}
