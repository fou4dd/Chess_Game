package com.Chess.engine.pieces;

import com.Chess.engine.Alliance;
import com.Chess.engine.board.Board;
import com.Chess.engine.board.BoardUtils;
import com.Chess.engine.board.Move;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.Chess.engine.board.Move.*;

public class Pawn extends Piece {

    private static final int[] CANDIDATE_MOVE_COORDINATE = {8, 16, 7, 9};

    public Pawn(final Alliance pieceAlliance, final int piecePosition) {
        super(piecePosition, pieceAlliance);
    }

    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {

        List<Move> legalMoves = new ArrayList<>();

        for(final int currentCandidateOffset : CANDIDATE_MOVE_COORDINATE){

            final int candidateDestinationCoordinate = this.piecePosition + (this.pieceAlliance.getDirection() * currentCandidateOffset);

            if(!BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)){
                continue;
            }

            if(currentCandidateOffset == 8 && !Board.getTile(candidateDestinationCoordinate).isTileOccupied()){
                legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
            }else if(currentCandidateOffset == 16 && this.isFirstMove() &&
                    (BoardUtils.SECOND_ROW[this.piecePosition] && this.pieceAlliance.isBlack()) ||
                    (BoardUtils.SEVENTH_ROW[this.piecePosition] && this.pieceAlliance.isWhite())){

                final int behindCandidateDestinationCoordinate = this.piecePosition + (this.pieceAlliance.getDirection() * 8);

                if(!board.getTile(behindCandidateDestinationCoordinate).isTileOccupied() &&
                   !board.getTile(candidateDestinationCoordinate).isTileOccupied()){
                        legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
                }else if((currentCandidateOffset == 7) &&
                        !(BoardUtils.EIGHTH_COLUMN[this.piecePosition] && this.pieceAlliance.isWhite() ||
                         BoardUtils.FIRST_COLUMN[this.piecePosition] && this.pieceAlliance.isBlack())){
                    if(board.getTile(candidateDestinationCoordinate).isTileOccupied()){
                        Piece pieceOnCandidate = board.getTile(candidateDestinationCoordinate).getPiece();
                        if(this.pieceAlliance != pieceOnCandidate.getPieceAlliance()){
                            legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
                        }
                    }
                }else if(currentCandidateOffset == 9 &&
                        !(BoardUtils.FIRST_COLUMN[this.piecePosition] && this.pieceAlliance.isWhite() ||
                          BoardUtils.EIGHTH_COLUMN[this.piecePosition] && this.pieceAlliance.isBlack())){
                    if(board.getTile(candidateDestinationCoordinate).isTileOccupied()){
                        Piece pieceOnCandidate = board.getTile(candidateDestinationCoordinate).getPiece();
                        if(this.pieceAlliance != pieceOnCandidate.getPieceAlliance()){
                            legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
                        }
                    }
                }

            }



        }

        return ImmutableList.copyOf(legalMoves);
    }
}
