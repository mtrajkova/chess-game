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
    public MoveResponseDto startGame(Long gameId) throws MoveGeneratorException {

        Game game = gameRepository.findById(gameId).orElseThrow(() -> new GameNotFoundException(gameId));

        game.setStatus(Status.ACTIVE);

        return getMoveResponseFromGame(game);
    }

    private MoveResponseDto getMoveResponseFromGame(Game game) throws MoveGeneratorException {
        Board board = getCurrentBoard(game);

        MoveResponseDto moveResponseDto = new MoveResponseDto();
        moveResponseDto.setFEN(board.getFen());
        moveResponseDto.setPGN(game.getPGN());
        moveResponseDto.setLegalMoves(MoveGenerator.generateLegalMoves(board));

        return moveResponseDto;
    }

    private Board getCurrentBoard(Game game) {
        String fen = game.getLastState().getFEN();
        Board board = new Board();
        board.loadFromFen(fen);

        return board;
    }

}
