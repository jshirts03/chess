package chess;

import java.util.Collection;

public class KnightGenerator extends MoveGenerator{
    public KnightGenerator(ChessBoard board, ChessPosition position){
        super(board, position);
    }

    //Overriding these directions because different movement rules for Knights

    @Override ChessMove checkForward(ChessPosition mover){
        ChessPosition forwardPosition = new ChessPosition(mover.getRow()+2, mover.getColumn()+1);
        if (isValidMove(forwardPosition)){
            return new ChessMove(myPosition, forwardPosition);
        }
        return null;
    }

    @Override ChessMove checkLeft(ChessPosition mover){
        ChessPosition forwardPosition = new ChessPosition(mover.getRow()-1, mover.getColumn()-2);
        if (isValidMove(forwardPosition)){
            return new ChessMove(myPosition, forwardPosition);
        }
        return null;
    }

    @Override ChessMove checkRight(ChessPosition mover){
        ChessPosition forwardPosition = new ChessPosition(mover.getRow()-1, mover.getColumn()+2);
        if (isValidMove(forwardPosition)){
            return new ChessMove(myPosition, forwardPosition);
        }
        return null;
    }

    @Override ChessMove checkBackward(ChessPosition mover){
        ChessPosition forwardPosition = new ChessPosition(mover.getRow()-2, mover.getColumn()-1);
        if (isValidMove(forwardPosition)){
            return new ChessMove(myPosition, forwardPosition);
        }
        return null;
    }

    public Collection<ChessMove> getMoves(){
        ChessMove nextMove = null;
        ChessPosition mover = myPosition;

        //Forward
        nextMove = checkForward(mover);
        if (nextMove != null){
            moves.add(nextMove);
        }

        //Left
        nextMove = checkLeft(mover);
        if (nextMove != null){
            moves.add(nextMove);
        }

        //Right
        nextMove = checkRight(mover);
        if (nextMove != null){
            moves.add(nextMove);
        }

        //Backward
        nextMove = checkBackward(mover);
        if (nextMove != null){
            moves.add(nextMove);
        }

        return moves;
    }

}
