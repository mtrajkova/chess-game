package com.project.chess.service.impl;

import com.project.chess.exception.GameNotFoundException;
import com.project.chess.model.Game;
import com.project.chess.repository.GameRepository;
import com.project.chess.service.GameService;
import com.project.chess.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final UserService userService;

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
        return gameRepository.save(game);
    }

    @Override
    public Game updateGameStatus(String newStatus, Long id) {
        return gameRepository.findById(id)
                .map(game -> {
                    game.setStatus(newStatus);
                    return gameRepository.save(game);
                })
                .orElseThrow(() -> new GameNotFoundException("Game " + id + " does not exist!"));
    }
}
