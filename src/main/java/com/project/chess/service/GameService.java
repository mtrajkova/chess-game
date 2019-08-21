package com.project.chess.service;

import com.github.bhlangonijr.chesslib.move.MoveGeneratorException;
import com.project.chess.model.Game;
import com.project.chess.model.Status;
import com.project.chess.model.dto.MoveResponseDto;
import com.project.chess.model.dto.MyGameDto;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

public interface GameService {

    Game getGameById(Long id);

    List<Game> getAllGamesByUser(Long userId);

    MyGameDto createGame(Game game);

    Game updateGameStatus(Status newStatus, Long id);

    SseEmitter getEmmiterToUser(Long id);

   // void sendEventsToEmitters();

    MoveResponseDto initializeGame() throws MoveGeneratorException;

    void startGame(Game game);
   // void sendEventsToEmitters();
}

