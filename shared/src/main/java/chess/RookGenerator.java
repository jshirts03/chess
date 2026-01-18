package chess;

import java.util.Collection;

public class RookGenerator extends MoveGenerator {
    public RookGenerator(ChessBoard board, ChessPosition position){
        super(board, position);
    }

    public Collection<ChessMove> getMoves() {
        ChessPosition mover = myPosition;
        ChessMove nextMove = null;

        //Check front
        do{
            nextMove = checkForward(mover);
            if (nextMove != null){
                moves.add(nextMove);
                mover = nextMove.endPosition;
                if (isCapture(nextMove)){    // stops the loop if move captures an enemy piece
                    nextMove = null;
                }
            }
        }while(nextMove != null);

        //Left
        mover = myPosition;
        do{
            nextMove = checkLeft(mover);
            if (nextMove != null){
                moves.add(nextMove);
                mover = nextMove.endPosition;
                if (isCapture(nextMove)){    // stops the loop if move captures an enemy piece
                    nextMove = null;
                }
            }
        }while(nextMove != null);

        //Right
        mover = myPosition;
        do{
            nextMove = checkRight(mover);
            if (nextMove != null){
                moves.add(nextMove);
                mover = nextMove.endPosition;
                if (isCapture(nextMove)){    // stops the loop if move captures an enemy piece
                    nextMove = null;
                }
            }
        }while(nextMove != null);

        //Backwards
        mover = myPosition;
        do{
            nextMove = checkBackward(mover);
            if (nextMove != null){
                moves.add(nextMove);
                mover = nextMove.endPosition;
                if (isCapture(nextMove)){    // stops the loop if move captures an enemy piece
                    nextMove = null;
                }
            }
        }while(nextMove != null);

        return moves;

    }
}
