package com.project.chess.Conterollers;

import com.project.chess.model.Game;
import com.project.chess.service.GameService;
import com.project.chess.service.impl.GameServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.PublicKey;


@RestController
@RequestMapping("/")
public class GameController {

    /*@Autowired*/
    private GameService gameService;

    @Autowired
    public GameController(GameServiceImpl gameService) {

        this.gameService = gameService;

    }

    @GetMapping(value = "/game/{id}")
    public ResponseEntity<?> getGameById(@PathVariable(value = "id") Long id) {
        Game returnedGame = gameService.getGameById(id);
        return new ResponseEntity<>(returnedGame, HttpStatus.OK);
    }

    @PostMapping(value = "/game/newGame")
    public ResponseEntity<?> sendRequestForGame(@RequestBody Game game){

        Game createdGame = gameService.createGame(game);

        return new ResponseEntity<>(createdGame,HttpStatus.OK);
    }



}
