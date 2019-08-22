package com.project.chess.service.impl;

import com.github.bhlangonijr.chesslib.Board;
import com.project.chess.exception.GameNotFoundException;
import com.project.chess.model.Game;
import com.project.chess.model.State;
import com.project.chess.model.Status;
import com.project.chess.model.dto.MyGameDto;
import com.project.chess.repository.GameRepository;
import com.project.chess.service.GameService;
import com.project.chess.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.*;
import java.util.stream.Collectors;

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
    public List<MyGameDto> getAllGamesByUser(Long userId) {
        List<MyGameDto> myGameDtos = new ArrayList<>();
//               myGameDtos = gameRepository.findAllByPlayerOneIdOrPlayerTwoId(userId,userId)
//                .stream()
//                       .map(game -> new MyGameDto(game.getId(),game.getPlayerTwo().getUsername(),game.getStatus(),game.getStartedDate(),game.getLastState().getFEN()))
//                .collect(Collectors.toList());
        List<Game> gamesWithUsers = gameRepository.findAllByPlayerOneIdOrPlayerTwoId(userId, userId);
        for (Game game : gamesWithUsers) {
            myGameDtos.add(MyGameDto.fromGame(game));
        }
        return myGameDtos;
        //return gameRepository.findAllByPlayerOneIdOrPlayerTwoId(userId, userId);
    }

    @Override
    public MyGameDto createGame(Game game) {
        game.setPlayerOne(userService.getUserByUsername(game.getPlayerOne().getUsername()));
        game.setPlayerTwo(userService.getUserByUsername(game.getPlayerTwo().getUsername()));
        game.setLastState(new State(new Board().getFen()));
        game.setPGN("");
        gameRepository.save(game);
        return new MyGameDto(game.getId(), game.getPlayerTwo().getDisplayName(), game.getStatus(), game.getStartedDate(), game.getLastState().getFEN(), game.getPGN());

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
