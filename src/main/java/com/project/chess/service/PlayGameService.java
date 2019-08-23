package com.project.chess.service;

import com.github.bhlangonijr.chesslib.move.MoveConversionException;
import com.github.bhlangonijr.chesslib.move.MoveGeneratorException;
import com.project.chess.model.Game;
import com.project.chess.model.dto.MoveDto;
import com.project.chess.model.dto.MoveResponseDto;

public interface PlayGameService {

    MoveResponseDto startGame(Long gameId) throws MoveGeneratorException;

    MoveResponseDto getMoveResponseOnMove(Game game, MoveDto moveDto) throws MoveGeneratorException, MoveConversionException;
}
