package chess;

import java.util.Collection;

public class BishopGenerator extends MoveGenerator{

    BishopGenerator(ChessBoard board, ChessPosition position){
        super(board, position);
    }

    Collection<ChessMove> getMoves(){
        ChessPosition mover = myPosition;
        ChessMove nextMove = null;

        //Up right checks
        do{
            nextMove = checkDiagonalUpRight(mover);
            if (nextMove != null){
                moves.add(nextMove);
                mover = nextMove.endPosition;
            }
        }while(nextMove != null);

        //Up left checks
//        do{
//            nextMove = checkDiagonalUpLeft(mover);
//        }

        return moves;

    }
}
