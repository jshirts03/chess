package chess;

import java.util.Arrays;
import java.util.Objects;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {
//Data Structure: Probably an array of arrays, within which can be stored individual Chess Piece objects
    ChessPiece[][] board;
    public ChessBoard() {
        this.board = new ChessPiece[8][8];
    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    //Adds a Chess Piece object (creates a new one) at a specific Chess Position object's row and column

    //Chess Piece objects have a piece type (King, Queen, Bishop, KNight, Rook, Pawn), and


    //Chess Position class contains 2 private integers, representing the row and column, stored in a 1-index pattern
    //The chessboard will be a 0 index array of arrays, so there must be a small adjustment when storing a ChessPiece object at a specific ChessPostion (-1)
    public void addPiece(ChessPosition position, ChessPiece piece) {
        board[position.row - 1][position.col - 1] = piece;
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    //returns the ChessPiece object at that location in the array of arrays, or null if there is none
    public ChessPiece getPiece(ChessPosition position) {
        return board[position.row -1][position.col -1];
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    //resets the board to the standard chess board
    public void resetBoard() {
        //Set the entire board to null
        for (int i=0; i<8; i++){
            for (int j=0; j<8; j++){
                board[i][j] = null;
            }
        }

//        Set up white pawns
        for (int i=0; i<8; i++){
            board[0][i] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        }
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append("Chess Board { \n");
        for (int i=0; i<=8; i++){
            for (int j=0; j<=8; j++){
                ChessPiece piece = board[i][j];
                ChessPiece.PieceType type = piece.getPieceType();
                ChessGame.TeamColor color = piece.getTeamColor();
                if (color == ChessGame.TeamColor.WHITE){
                    string.append("[W");
                }
                else{
                    string.append("[B");
                }
                switch (type) {
                    case PAWN:
                        string.append("P]");
                        break;
                    case ROOK:
                        string.append("R]");
                        break;
                    case KNIGHT:
                        string.append("N]");
                        break;
                    case BISHOP:
                        string.append("B]");
                        break;
                    case QUEEN:
                        string.append("Q]");
                        break;
                    case KING:
                        string.append("K]");
                        break;
                }
                string.append(" ");
            }
            string.append("\n");
        }
        return string.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ChessBoard that)) {
            return false;
        }
        return Objects.deepEquals(board, that.board);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(board);
    }
}
