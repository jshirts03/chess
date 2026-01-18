package chess;

import java.util.Collection;

public class KnightGenerator extends MoveGenerator{
    public KnightGenerator(ChessBoard board, ChessPosition position){
        super(board, position);
    }

    //Overriding these directions because different movement rules for Knights
    ChessMove knightMove(ChessPosition mover, int forwardFactor, int lateralFactor){
        ChessPosition forwardPosition = new ChessPosition(mover.getRow()+forwardFactor, mover.getColumn()+lateralFactor);
        if (isValidMove(forwardPosition)){
            return new ChessMove(myPosition, forwardPosition);
        }
        return null;
    }


    public Collection<ChessMove> getMoves(){
        ChessMove nextMove = null;
        ChessPosition mover = myPosition;

        //Forward
        nextMove = knightMove(mover, 2, 1);
        if (nextMove != null){
            moves.add(nextMove);
        }
        nextMove = knightMove(mover, 2, -1);
        if (nextMove != null){
            moves.add(nextMove);
        }

        //Left
        nextMove = knightMove(mover, -1, -2);
        if (nextMove != null){
            moves.add(nextMove);
        }
        nextMove = knightMove(mover, 1, -2);
        if (nextMove != null){
            moves.add(nextMove);
        }

        //Right
        nextMove = knightMove(mover, -1, +2);
        if (nextMove != null){
            moves.add(nextMove);
        }
        nextMove = knightMove(mover, 1, +2);
        if (nextMove != null){
            moves.add(nextMove);
        }

        //Backward
        nextMove = knightMove(mover, -2, +1);
        if (nextMove != null){
            moves.add(nextMove);
        }
        nextMove = knightMove(mover, -2, -1);
        if (nextMove != null){
            moves.add(nextMove);
        }
        return moves;
    }

}
