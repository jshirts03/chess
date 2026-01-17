package chess;

import java.util.Collection;

public class PawnGenerator extends MoveGenerator{

    public PawnGenerator(ChessBoard board, ChessPosition position){
        super(board, position);
    }

    //This overrides the checkForward function because Pawns cannot move on top of opposing pieces that are in front of them
    @Override
    public ChessMove checkForward(ChessPosition mover){
        int row = mover.getRow();
        int col = mover.getColumn();
        ChessPosition forwardPosition = new ChessPosition(row+1, col);
        if (forwardPosition.getRow() < 1 || forwardPosition.getRow() > 8){
            return null;
        }
        if (forwardPosition.getColumn() < 1 || forwardPosition.getColumn() > 8){
            return null;
        }
        if (board.getPiece(forwardPosition) != null){
            return null;
        }
        return new ChessMove(myPosition, forwardPosition);
    }
    // Generate moves
    Collection<ChessMove> getMoves(){
        ChessPosition mover = myPosition;

        ChessMove nextMove = checkForward(mover);
        if (nextMove != null){
            moves.add(nextMove);
        }

        if (myPosition.getRow()==2){
            mover = new ChessPosition(mover.getRow()+1, mover.getColumn());
            nextMove = checkForward(mover);
            if (nextMove != null){
                moves.add(nextMove);
            }
        }

        return moves;
    }
    //Checks forward
    //Generator function that checks 1 ahead
    //Use while loops to continue in that direction, calling over and over again until it hits an invalid move

    //
}
