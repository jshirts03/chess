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

    public boolean isValidMove(ChessPosition position){
        int row = position.getRow();
        int col = position.getColumn();
        if (row < 1 || row > 8 || col < 1 || col > 8){
            return false;
        }
        ChessGame.TeamColor targetColor = board.getPiece(position).getTeamColor();
        if (targetColor == myColor){
            return false;
        }
        return true;
    }

    ChessMove checkForward(ChessPosition mover){
        ChessPosition forwardPosition = null;
        if (myColor == ChessGame.TeamColor.WHITE){
            forwardPosition = new ChessPosition(mover.getRow()+1, mover.getColumn());
        }
        else{
            forwardPosition = new ChessPosition(mover.getRow()-1, mover.getColumn());
        }
        if (isValidMove(forwardPosition)){
            return new ChessMove(myPosition, forwardPosition);
        }
        return null;
    }

    ChessMove checkDiagonalLeft(ChessPosition mover){
        ChessPosition diagonalPosition = null;
        if (myColor == ChessGame.TeamColor.WHITE){
            diagonalPosition = new ChessPosition(mover.getRow()+1, mover.getColumn()-1);
        }
        else{
            diagonalPosition = new ChessPosition(mover.getRow()-1, mover.getColumn()+1);
        }
        if (isValidMove(diagonalPosition)){
            return new ChessMove(myPosition, diagonalPosition);
        }
        return null;
    }

    ChessMove checkDiagonalRight(ChessPosition mover){
        ChessPosition diagonalPosition = null;
        if (myColor == ChessGame.TeamColor.WHITE){
            diagonalPosition = new ChessPosition(mover.getRow()+1, mover.getColumn()-1);
        }
        else{
            diagonalPosition = new ChessPosition(mover.getRow()-1, mover.getColumn()+1);
        }
        if (isValidMove(diagonalPosition)){
            return new ChessMove(myPosition, diagonalPosition);
        }
        return null;
    }
}
