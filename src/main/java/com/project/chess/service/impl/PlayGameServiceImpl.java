package com.project.chess.service.impl;

import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.move.MoveGenerator;
import com.github.bhlangonijr.chesslib.move.MoveGeneratorException;
import com.project.chess.exception.GameNotFoundException;
import com.project.chess.model.Game;
import com.project.chess.model.Status;
import com.project.chess.model.dto.MoveResponseDto;
import com.project.chess.repository.GameRepository;
import com.project.chess.service.PlayGameService;
import org.springframework.stereotype.Service;

@Service
public class PlayGameServiceImpl implements PlayGameService {
    private final GameRepository gameRepository;

    public PlayGameServiceImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

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
    public void startGame(Long gameId) {
        Game gameToUpdate = gameRepository.findById(gameId).orElseThrow(() -> new GameNotFoundException("Game " + gameId + " does not exist!"));

        gameToUpdate.setStatus(Status.ACTIVE);

        gameRepository.save(gameToUpdate);
    }
}
