package com.project.chess.exception;

public class GameNotFoundException extends CustomException {

    public GameNotFoundException(Long id) {
        super("Game with id " + id + " does not exist");
    }


}
