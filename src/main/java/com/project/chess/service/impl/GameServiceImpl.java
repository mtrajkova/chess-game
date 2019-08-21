package com.project.chess.service.impl;

import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.move.MoveGenerator;
import com.github.bhlangonijr.chesslib.move.MoveGeneratorException;
import com.project.chess.exception.GameNotFoundException;
import com.project.chess.model.Game;
import com.project.chess.model.Status;
import com.project.chess.model.dto.MoveResponseDto;
import com.project.chess.model.dto.MyGameDto;
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
    public MyGameDto createGame(Game game) {
        game.setPlayerOne(userService.getUserById(game.getPlayerOne().getId()));
        game.setPlayerTwo(userService.getUserById(game.getPlayerTwo().getId()));
        gameRepository.save(game);
        MyGameDto myGameDto = new MyGameDto(game.getId(), game.getPlayerTwo().getDisplayName(), game.getStatus(), game.getStartedDate());
        return myGameDto;
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

    /*@Override
    public void sendEventsToEmitters() {
        sseEmitterMap.forEach((aLong, sseEmitter) -> {
            try {
                sseEmitter.send(new Game(), MediaType.ALL);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }*/

    @Override
    public MoveResponseDto initializeGame() throws MoveGeneratorException {
        MoveResponseDto moveResponseDto = new MoveResponseDto();
        Board board = new Board();

        moveResponseDto.setFEN(board.getFen());
        moveResponseDto.setPGN("");
        moveResponseDto.setLegalMoves(MoveGenerator.generateLegalMoves(board));

        return moveResponseDto;
    }

    @Override
    public void startGame(Game game) {
        Game gameToUpdate = gameRepository.findById(game.getId()).orElseThrow(() -> new GameNotFoundException("Game not found!"));

        gameToUpdate.setStatus(Status.ACTIVE);

        gameRepository.save(gameToUpdate);
    }

}
