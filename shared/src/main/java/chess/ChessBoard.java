package chess;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {
//Data Structure: Probably an array of arrays, within which can be stored individual Chess Piece objects
    public ChessBoard() {
        
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
        throw new RuntimeException("Not implemented");
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
        throw new RuntimeException("Not implemented");
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    //resets the board to the standard chess board
    public void resetBoard() {
        throw new RuntimeException("Not implemented");
    }
}
