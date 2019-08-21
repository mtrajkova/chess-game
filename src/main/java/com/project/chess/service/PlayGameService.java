package com.project.chess.service;

import com.github.bhlangonijr.chesslib.move.MoveGeneratorException;
import com.project.chess.model.dto.MoveResponseDto;

public interface PlayGameService {

    MoveResponseDto initializeGame() throws MoveGeneratorException;

    void startGame(Long gameId);
}
