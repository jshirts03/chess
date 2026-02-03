package chess;

public class KingFinder {
    private ChessBoard board;
    private ChessGame.TeamColor teamColor;

    public KingFinder(ChessGame.TeamColor teamColor, ChessBoard board){
        this.board = board;
        this.teamColor = teamColor;
    }

    boolean isKing(ChessPiece piece){
        if (piece == null){
            return false;
        }
        return (piece.type == ChessPiece.PieceType.KING && piece.color == teamColor);
    }

    ChessPosition getKing(){
        for (int i=1; i < 9; i++){
            for (int j=1; j < 9; j++){
                ChessPosition searcher = new ChessPosition(i, j);
                ChessPiece searchPiece = board.getPiece(searcher);
                if (isKing(searchPiece)){
                    return searcher;
                }
            }
        }
        return null;
    }
}
