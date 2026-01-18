package chess;

import java.util.ArrayList;
import java.util.Collection;

public class MoveGenerator
{
    ChessBoard board;
    ChessPosition myPosition;
    ChessGame.TeamColor myColor;
    ArrayList<ChessMove> moves = new ArrayList<ChessMove>();

    public MoveGenerator(ChessBoard board, ChessPosition myPosition){
        this.board = board;
        this.myPosition = myPosition;
        this.myColor = board.getPiece(myPosition).getTeamColor();
    }

    //Used by subclasses to see if a chessmove is a capture(move ends on a chesspiece), knowing when to stop while loops
    public boolean isCapture(ChessMove move){
        ChessPiece targetPiece = board.getPiece(move.endPosition);
        if (targetPiece != null){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean isValidMove(ChessPosition position){
        int row = position.getRow();
        int col = position.getColumn();
        if (row < 1 || row > 8 || col < 1 || col > 8){
            return false;
        }
        ChessPiece targetPiece = board.getPiece(position);
        if (targetPiece != null){
            ChessGame.TeamColor targetColor = targetPiece.getTeamColor();
            if (targetColor == myColor){
                return false;
            }
        }
        return true;
    }


    ChessMove checkForward(ChessPosition mover){
        ChessPosition forwardPosition = new ChessPosition(mover.getRow()+1, mover.getColumn());
        if (isValidMove(forwardPosition)){
            return new ChessMove(myPosition, forwardPosition);
        }
        return null;
    }

    ChessMove checkDiagonalUpLeft(ChessPosition mover){
        ChessPosition diagonalPosition = new ChessPosition(mover.getRow()+1, mover.getColumn()-1);
        if (isValidMove(diagonalPosition)){
            return new ChessMove(myPosition, diagonalPosition);
        }
        return null;
    }

    ChessMove checkDiagonalUpRight(ChessPosition mover){
        ChessPosition diagonalPosition = new ChessPosition(mover.getRow()+1, mover.getColumn()+1);
        if (isValidMove(diagonalPosition)){
            return new ChessMove(myPosition, diagonalPosition);
        }
        return null;
    }

    ChessMove checkDiagonalDownRight(ChessPosition mover){
        ChessPosition diagonalPosition = new ChessPosition(mover.getRow()-1, mover.getColumn()+1);
        if (isValidMove(diagonalPosition)){
            return new ChessMove(myPosition, diagonalPosition);
        }
        return null;
    }

    ChessMove checkDiagonalDownLeft(ChessPosition mover){
        ChessPosition diagonalPosition = new ChessPosition(mover.getRow()-1, mover.getColumn()-1);
        if (isValidMove(diagonalPosition)){
            return new ChessMove(myPosition, diagonalPosition);
        }
        return null;
    }


}
