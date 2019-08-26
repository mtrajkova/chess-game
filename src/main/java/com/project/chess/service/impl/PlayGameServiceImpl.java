package com.project.chess.service.impl;

import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.Piece;
import com.github.bhlangonijr.chesslib.Side;
import com.github.bhlangonijr.chesslib.Square;
import com.github.bhlangonijr.chesslib.game.GameResult;
import com.github.bhlangonijr.chesslib.move.*;
import com.project.chess.exception.GameNotFoundException;
import com.project.chess.model.Game;
import com.project.chess.model.State;
import com.project.chess.model.Status;
import com.project.chess.model.dto.MoveDto;
import com.project.chess.model.dto.MoveResponseDto;
import com.project.chess.model.Result;
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
        moveResponseDto.setFEN(game.getLastState().getFEN());
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

    @Override
    public MoveResponseDto getMoveResponseOnMove(Game game, MoveDto moveDto) throws MoveGeneratorException, MoveConversionException {

        Move move = new Move(Square.fromValue(moveDto.getFrom().toUpperCase()), Square.fromValue(moveDto.getTo().toUpperCase()), Piece.fromValue(moveDto.getPromotion().toUpperCase()));

        validateAndUpdateGame(game, move);

        return getMoveResponseFromGame(game);
    }

    private void validateAndUpdateGame(Game game, Move move) throws MoveGeneratorException, MoveConversionException {

        Board board = getCurrentBoard(game);

        if (MoveGenerator.generateLegalMoves(board).contains(move)) {
            board.doMove(move);

            game.setLastState(new State(board.getFen()));
            game.setPGN(updatePgnOnMove(game, move));
        }
    }

    private String updatePgnOnMove(Game game, Move move) throws MoveConversionException {

        MoveList movesHistoryPgn = new MoveList();
        movesHistoryPgn.loadFromSan(game.getPGN());
        movesHistoryPgn.add(move);
        return movesHistoryPgn.toSan();
    }

    private Result checkResult(Game game) {

        Board board = getCurrentBoard(game);

        if (board.isMated()) {
            if (board.getSideToMove().equals(Side.BLACK)) {
                return Result.WHITE_WON_CHECKMATE;
            }
        }
        return null;
    }
}
