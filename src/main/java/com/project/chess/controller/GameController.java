package com.project.chess.controller;

import com.project.chess.model.Game;
import com.project.chess.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/game")
public class GameController {

    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<Game> getGameById(@PathVariable(value = "id") Long id) {
        Game returnedGame = gameService.getGameById(id);
        return new ResponseEntity<>(returnedGame, HttpStatus.OK);
    }

    @PostMapping(value = "/new")
    public ResponseEntity<Game> sendRequestForGame(@RequestBody Game game) {
        Game createdGame = gameService.createGame(game);
        return new ResponseEntity<>(createdGame, HttpStatus.CREATED);
    }

    @PutMapping(value = "/update")
    public ResponseEntity<?> updateGameStatus(@RequestParam(value = "id") Long id, @RequestParam(value = "status") String status) {
        Game updatedGame = gameService.updateGameStatus(status, id);
        return new ResponseEntity<>(updatedGame, HttpStatus.OK);
    }
}
