package com.project.chess.service;

import com.project.chess.model.Game;
import com.project.chess.model.Status;
import com.project.chess.model.dto.MyGameDto;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

public interface GameService {

    Game getGameById(Long id);

    List<MyGameDto> getAllGamesByUser(Long userId);

    MyGameDto createGame(Game game);

    Game updateGameStatus(Status newStatus, Long id);

    SseEmitter getEmmiterToUser(Long id);

   // void sendEventsToEmitters();



}

