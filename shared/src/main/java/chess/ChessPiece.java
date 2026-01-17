package chess;

import java.util.Collection;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {

    //class variable that's type, which is a Piece Type constant as defined below
    //class variable pieceColor which is either WHITE or BLACK as found in the ChessGame.TeamColor variable
    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        throw new RuntimeException("Not implemented");
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    //returns an ArrayList of ChessMoves that a specific chess piece can move to. Should use the different generator classes.
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        throw new RuntimeException("Not implemented");
    }

    //to check if there's one of your pieces in the way for your move (check the double array to see if a piece with your team's color is there)
    //move calculator classes with a parent class that puts the common code in it
    //depending on what the piecetype is, then

    //ex. moveGenerator = new moveGenerator(ChessBoard, ChessPosition)
    //    moves = moveGenerator.get_moves(ChessBoard, ChessPosition)

            //get_moves method

    // piece moves subclasses that create instances of each subclass depending on what it is.
    //get the piece type of the piece at the chessposition on the board
    //switch or if statement that will generate a generator instance depending on what type it is.

    //ex. queenGenerator = new QueenGenerator(ChessBoard, ChessPosition)
    //ex. moves = queenGenerator.get_moves()

            //Queen generator get_moves()

                //looks through the chess board and runs an algorithm according to the queen's movement rules
                //returns an ArrayList of PieceMove objects with starting point, endpoint
}
