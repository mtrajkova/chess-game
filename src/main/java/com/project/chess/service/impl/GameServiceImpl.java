package com.project.chess.service.impl;

import com.project.chess.model.Game;
import com.project.chess.service.GameService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameServiceImpl implements GameService {
    @Override
    public Game getGameById(Long id) {
        return null;
    }

    @Override
    public List<Game> getAllGamesByUser(Long userId) {
        return null;
    }

    @Override
    public Game createGame(Game game) {
        return null;
    }

    @Override
    public Game updateGameStatus(String newStatus, Long id) {
        return null;
    }
}
