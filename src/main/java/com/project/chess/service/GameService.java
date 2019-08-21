package com.project.chess.service;

import com.project.chess.model.Game;
import com.project.chess.model.Status;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

public interface GameService {

    Game getGameById(Long id);

    List<Game> getAllGamesByUser(Long userId);

    Game createGame(Game game);

    Game updateGameStatus(Status newStatus, Long id);

    SseEmitter getEmmiterToUser(Long id);

    void sendEventsToEmitters();
}

