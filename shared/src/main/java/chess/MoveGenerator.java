package chess;

public class MoveGenerator
{
    ChessBoard board;
    ChessPosition myPosition;
    ChessGame.TeamColor myColor;

    MoveGenerator(ChessBoard board, ChessPosition myPosition){
        this.board = board;
        this.myPosition = myPosition;
        this.myColor = board.getPiece(myPosition).getTeamColor();
    }

    boolean isValidMove(ChessPosition position){
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
}
