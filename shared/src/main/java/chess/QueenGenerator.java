package chess;

import java.util.Collection;

public class QueenGenerator extends MoveGenerator{
    public QueenGenerator(ChessBoard board, ChessPosition position){
        super(board, position);
    }

    public Collection<ChessMove> getMoves(){
        ChessPosition mover = myPosition;
        ChessMove nextMove = null;

        //Up right checks
        do{
            nextMove = checkDiagonalUpRight(mover);
            if (nextMove != null){
                moves.add(nextMove);
                mover = nextMove.endPosition;
                if (isCapture(nextMove)){    // stops the loop if move captures an enemy piece
                    nextMove = null;
                }
            }
        }while(nextMove != null);

        //Up left checks
        mover = myPosition;
        do{
            nextMove = checkDiagonalUpLeft(mover);
            if (nextMove != null){
                moves.add(nextMove);
                mover = nextMove.endPosition;
                if (isCapture(nextMove)){    // stops the loop if move captures an enemy piece
                    nextMove = null;
                }
            }
        }while(nextMove != null);

        //Down Left Checks
        mover = myPosition;
        do{
            nextMove = checkDiagonalDownLeft(mover);
            if (nextMove != null){
                moves.add(nextMove);
                mover = nextMove.endPosition;
                if (isCapture(nextMove)){    // stops the loop if move captures an enemy piece
                    nextMove = null;
                }
            }

        }while(nextMove != null);

        //Down Right Checks
        mover = myPosition;
        do{
            nextMove = checkDiagonalDownRight(mover);
            if (nextMove != null){
                moves.add(nextMove);
                mover = nextMove.endPosition;
                if (isCapture(nextMove)){    // stops the loop if move captures an enemy piece
                    nextMove = null;
                }
            }
        }while(nextMove != null);

        //Forward Checks
        mover = myPosition;
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

        //Left Checks
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

        //Right Checks
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

        //Down Checks
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
