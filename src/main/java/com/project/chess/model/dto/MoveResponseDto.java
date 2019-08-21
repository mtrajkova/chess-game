package com.project.chess.model.dto;

import com.github.bhlangonijr.chesslib.move.MoveList;

public class MoveResponseDto {
    private MoveList legalMoves;
    private String FEN;
    private String PGN;

    public MoveList getLegalMoves() {
        return legalMoves;
    }

    public void setLegalMoves(MoveList legalMoves) {
        this.legalMoves = legalMoves;
    }

    public String getFEN() {
        return FEN;
    }

    public void setFEN(String FEN) {
        this.FEN = FEN;
    }

    public String getPGN() {
        return PGN;
    }

    public void setPGN(String PGN) {
        this.PGN = PGN;
    }
}
