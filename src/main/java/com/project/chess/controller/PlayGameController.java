package com.project.chess.controller;

import com.github.bhlangonijr.chesslib.move.MoveGeneratorException;
import com.project.chess.model.dto.MoveResponseDto;
import com.project.chess.service.PlayGameService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/play")
public class PlayGameController {
    private final PlayGameService gameService;

    public PlayGameController(PlayGameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<MoveResponseDto> startGame(@PathVariable Long id) {
        try {
            MoveResponseDto moveResponseDto = gameService.startGame(id);

            return ResponseEntity.status(HttpStatus.OK).body(moveResponseDto);
        } catch (MoveGeneratorException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
