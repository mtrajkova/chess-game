package com.project.chess.service.impl;

import com.project.chess.exception.GameNotFoundException;
import com.project.chess.model.Game;
import com.project.chess.model.Status;
import com.project.chess.repository.GameRepository;
import com.project.chess.service.GameService;
import com.project.chess.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final UserService userService;

    private static Map<Long, SseEmitter> sseEmitterMap = Collections.synchronizedMap(new HashMap<>());

    @Autowired
    public GameServiceImpl(GameRepository gameRepository, UserServiceImpl userService) {
        this.gameRepository = gameRepository;
        this.userService = userService;
    }

    @Override
    public Game getGameById(Long id) {
        return gameRepository.findById(id)
                .orElseThrow(() -> new GameNotFoundException("Game " + id + " does not exist!"));
    }

    @Override
    public List<Game> getAllGamesByUser(Long userId) {
        return gameRepository.findAllByPlayerOneIdOrPlayerTwoId(userId, userId);
    }

    @Override
    public Game createGame(Game game) {
        game.setPlayerOne(userService.getUserById(game.getPlayerOne().getId()));
        game.setPlayerTwo(userService.getUserById(game.getPlayerTwo().getId()));
        gameRepository.save(game);
        try {
            sseEmitterMap.get(game.getPlayerTwo().getId()).send(game, MediaType.APPLICATION_JSON);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return game;
    }

    @Override
    public Game updateGameStatus(Status newStatus, Long id) {
        return gameRepository.findById(id)
                .map(game -> {
                    game.setStatus(newStatus);
                    return gameRepository.save(game);
                })
                .orElseThrow(() -> new GameNotFoundException("Game " + id + " does not exist!"));
    }

    @Override
    public SseEmitter getEmmiterToUser(Long id) {
        sseEmitterMap.put(id, new SseEmitter());
        return sseEmitterMap.get(id);

    }

    @Override
    public void sendEventsToEmitters() {
        sseEmitterMap.forEach((aLong, sseEmitter) -> {
            try {
                sseEmitter.send(new Game(), MediaType.ALL);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
