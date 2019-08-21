package com.project.chess.model.dto;

import com.github.bhlangonijr.chesslib.move.MoveList;

public class LegalMovesDto {
    private MoveList legalMoves;

    public MoveList getLegalMoves() {
        return legalMoves;
    }

    public void setLegalMoves(MoveList legalMoves) {
        this.legalMoves = legalMoves;
    }
}
