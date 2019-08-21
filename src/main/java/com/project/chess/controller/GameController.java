package com.project.chess.controller;

import com.project.chess.model.Game;
import com.project.chess.model.Status;
import com.project.chess.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

@CrossOrigin("*")
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

    @GetMapping(value = "/emitter/{id}")
    public SseEmitter getEmitter(@PathVariable(value = "id") Long id){
            return gameService.getEmmiterToUser(id);
    }

    @GetMapping(value = "/user-games/{id}")
    public ResponseEntity<List<Game>> getAllGamesForUser(@PathVariable(value = "id") Long id){
        return new ResponseEntity<>(gameService.getAllGamesByUser(id),HttpStatus.OK);
    }

    @PostMapping(value = "/new")
    public ResponseEntity<Game> sendRequestForGame(@RequestBody Game game) {
        Game createdGame = gameService.createGame(game);
        return new ResponseEntity<>(createdGame, HttpStatus.CREATED);
    }

    @PutMapping(value = "/update")
    public ResponseEntity<Game> updateGameStatus(@RequestParam(value = "id") Long id, @RequestParam(value = "status") Status status) {
        Game updatedGame = gameService.updateGameStatus(status, id);
        return new ResponseEntity<>(updatedGame, HttpStatus.OK);
    }

    @GetMapping(value = "/test")
    public void test(){
        gameService.sendEventsToEmitters();
    }
}
