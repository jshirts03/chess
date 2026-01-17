package chess;

import java.util.Collection;

public class PawnGenerator extends MoveGenerator{

    public PawnGenerator(ChessBoard board, ChessPosition position){
        super(board, position);
    }

    //This overrides the checkForward function because Pawns cannot move on top of opposing pieces that are in front of them
    @Override
    public ChessMove checkForward(ChessPosition mover){
        ChessPosition forwardPosition = null;
        if (myColor == ChessGame.TeamColor.WHITE){
            forwardPosition = new ChessPosition(mover.getRow()+1, mover.getColumn());
        }
        else{
            forwardPosition = new ChessPosition(mover.getRow()-1, mover.getColumn());
        }
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

    //Ovverride because it can only move diagonally if it is killing an enemy pawn
    @Override
    public ChessMove checkDiagonalRight(ChessPosition mover){
        ChessPosition diagonalPosition = null;
        if (myColor == ChessGame.TeamColor.WHITE){
            diagonalPosition = new ChessPosition(mover.getRow()+1, mover.getColumn()-1);
        }
        else{
            diagonalPosition = new ChessPosition(mover.getRow()-1, mover.getColumn()+1);
        }
        if (diagonalPosition.getRow() < 1 || diagonalPosition.getRow() > 8){
            return null;
        }
        if (diagonalPosition.getColumn() < 1 || diagonalPosition.getColumn() > 8){
            return null;
        }
        if (board.getPiece(diagonalPosition) == null){
            return null;
        }
        if (board.getPiece(diagonalPosition).getTeamColor() != ChessGame.TeamColor.BLACK){
            return null;
        }
        return new ChessMove(myPosition, diagonalPosition);
    }

    //Override because pawns can only move diagonal if they are killing enemy piece
    @Override
    public ChessMove checkDiagonalLeft(ChessPosition mover){
        ChessPosition diagonalPosition = null;
        if (myColor == ChessGame.TeamColor.WHITE){
            diagonalPosition = new ChessPosition(mover.getRow()+1, mover.getColumn()-1);
        }
        else{
            diagonalPosition = new ChessPosition(mover.getRow()-1, mover.getColumn()+1);
        }
        if (diagonalPosition.getRow() < 1 || diagonalPosition.getRow() > 8){
            return null;
        }
        if (diagonalPosition.getColumn() < 1 || diagonalPosition.getColumn() > 8){
            return null;
        }
        if (board.getPiece(diagonalPosition).getTeamColor() != ChessGame.TeamColor.BLACK){
            return null;
        }
        return new ChessMove(myPosition, diagonalPosition);
    }




    // Generate moves
    Collection<ChessMove> getMoves(){
        ChessPosition mover = myPosition;

        //Checks the diagonals
        ChessMove nextMove = checkDiagonalLeft(mover);
        if (nextMove != null){
            moves.add(nextMove);
        }
        nextMove = checkDiagonalRight(mover);
        if (nextMove != null){
            moves.add(nextMove);
        }

        //Checks 1 forward
        nextMove = checkForward(mover);
        if (nextMove != null){
            moves.add(nextMove);
        }

        //If it's in starting row can move 2 forward
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
