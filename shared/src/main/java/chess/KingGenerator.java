package chess;

import java.util.Collection;

public class KingGenerator extends MoveGenerator{
    public KingGenerator(ChessBoard board, ChessPosition position){
        super(board, position);
    }

    public Collection<ChessMove> getMoves(){
        ChessPosition mover = myPosition;
        ChessMove nextMove = null;

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

        //UpRight
        nextMove = checkDiagonalUpRight(mover);
        if (nextMove != null){
            moves.add(nextMove);
        }

        //UpLeft
        nextMove = checkDiagonalUpLeft(mover);
        if (nextMove != null){
            moves.add(nextMove);
        }

        //DownLeft
        nextMove = checkDiagonalDownLeft(mover);
        if (nextMove != null){
            moves.add(nextMove);
        }

        //DownRight
        nextMove = checkDiagonalDownRight(mover);
        if (nextMove != null){
            moves.add(nextMove);
        }

        return moves;
    }

}
