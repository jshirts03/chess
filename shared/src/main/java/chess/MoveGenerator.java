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
        int row = mover.getRow();
        int col = mover.getColumn();
        ChessPosition forwardPosition = new ChessPosition(row+1, col);
        if (isValidMove(forwardPosition)){
            return new ChessMove(mover, forwardPosition);
        }
        return null;
    }
}
