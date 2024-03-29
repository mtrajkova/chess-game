package com.project.chess.service.impl;

import com.github.bhlangonijr.chesslib.Board;
import com.project.chess.exception.GameNotFoundException;
import com.project.chess.exception.UserNotFoundException;
import com.project.chess.model.Game;
import com.project.chess.model.State;
import com.project.chess.model.Status;
import com.project.chess.model.dto.MyGameDto;
import com.project.chess.repository.GameRepository;
import com.project.chess.repository.UserRepository;
import com.project.chess.service.GameService;
import com.project.chess.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.*;

@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    //private static Map<Long, SseEmitter> sseEmitterMap = Collections.synchronizedMap(new HashMap<>());

    @Autowired
    public GameServiceImpl(GameRepository gameRepository, UserServiceImpl userService, UserRepository userRepository) {
        this.gameRepository = gameRepository;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @Override
    public Game getGameById(Long id) {
        return gameRepository.findById(id)
                .orElseThrow(() -> new GameNotFoundException(id));
    }

    @Override
    public List<MyGameDto> getAllGamesByUser(Long userId) {
        userRepository.findById(userId).orElseThrow(()->new UserNotFoundException(userId));
        List<MyGameDto> myGameDtos = new ArrayList<>();
        List<Game> gamesWithUsers = gameRepository.findAllByPlayerOneIdOrPlayerTwoId(userId, userId);
        for (Game game : gamesWithUsers) {
            myGameDtos.add(MyGameDto.fromGame(game, userId));
        }
        return myGameDtos;
    }

    @Override
    public MyGameDto createGame(Game game) {
        game.setPlayerOne(userService.getUserByUsername(game.getPlayerOne().getUsername()));
        game.setPlayerTwo(userService.getUserByUsername(game.getPlayerTwo().getUsername()));
        game.setLastState(new State(new Board().getFen()));
        game.setPGN("");
        game.setInviter(userService.getUserByUsername(game.getPlayerOne().getUsername()).getId());
        gameRepository.save(game);
        return new MyGameDto(game.getId(), game.getPlayerOne().getDisplayName(), game.getStatus(), game.getStartedDate(), game.getLastState().getFEN(), game.getPGN());

    }

    @Override
    public Game updateGameStatus(Status newStatus, Long id) {
        return gameRepository.findById(id)
                .map(game -> {
                    game.setStatus(newStatus);
                    return gameRepository.save(game);
                })
                .orElseThrow(() -> new GameNotFoundException(id));
    }


    @Override
    public SseEmitter getEmmiterToUser(Long id) {

        SsEmitter.setSseEmitterMap(id, new SseEmitter());

        return SsEmitter.getSseEmitterMap().get(id);
    }
/*
    @Override
    public void sendEventsToEmitters() {
        sseEmitterMap.forEach((aLong, sseEmitter) -> {
            try {
                sseEmitter.send(new Game(), MediaType.ALL);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }*/

}
