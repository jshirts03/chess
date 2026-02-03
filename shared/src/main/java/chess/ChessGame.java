package chess;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {

    private TeamColor teamTurn;
    private ChessBoard board;

    public ChessGame() {
        board = new ChessBoard();
        board.resetBoard();
        teamTurn = TeamColor.WHITE;
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return teamTurn;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        teamTurn = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    //calls the getMoves on the specific piece, then verifies if any of those moves would put the King in check, eliminate those moves
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        HashSet<ChessMove> validMoves = new HashSet<ChessMove>();
        ChessPiece targetPiece = board.getPiece(startPosition);
        Collection<ChessMove> moves = targetPiece.pieceMoves(board, startPosition);
        SimulatedGame simGame = new SimulatedGame(board);
        for (ChessMove move : moves){
            simGame.simulateMove(move);
            if (!simGame.isInCheck(targetPiece.getTeamColor())){
                validMoves.add(move);
            }
            simGame.resetBoard();
        }
        return validMoves;

    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to perform
     * @throws InvalidMoveException if move is invalid
     */
    //this will call validMoves on the startPosition of the move
    //then checks to make sure that the move is in the list of validated moves
    //throws exception if it is not valid, else moves the specified piece to the specified location
    public void makeMove(ChessMove move) throws InvalidMoveException {
        if (board.getPiece(move.getStartPosition()) == null){
            throw new InvalidMoveException();
        }
        Collection<ChessMove> validMoves = validMoves(move.getStartPosition());
        ChessGame.TeamColor targetColor = board.getPiece(move.getStartPosition()).getTeamColor();
        if (!validMoves.contains(move) || targetColor != teamTurn){
            throw new InvalidMoveException();
        }
        ChessPiece targetPiece = null;
        if (move.getPromotionPiece() != null){
            targetPiece = new ChessPiece(targetColor, move.getPromotionPiece());
        }
        else{
            targetPiece = board.getPiece(move.getStartPosition());
        }
        board.addPiece(move.getEndPosition(), targetPiece);
        board.addPiece(move.getStartPosition(), null);
        teamTurn = (teamTurn == TeamColor.WHITE) ? TeamColor.BLACK : TeamColor.WHITE;
    }




    public boolean isDangerous(ChessPiece searchPiece, ChessPosition searchPos, ChessPosition kingPosition, TeamColor teamColor){
        if (searchPiece == null){
            return false;
        }
        if (searchPiece.getTeamColor() == teamColor){
            return false;
        }
        Collection<ChessMove> moves = searchPiece.pieceMoves(board, searchPos);
        for (ChessMove move : moves){
            if (move.getEndPosition().equals(kingPosition)){
                return true;
            }
        }
        return false;
    }


    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */

    public boolean isInCheck(TeamColor teamColor) {
        //find the king
        //for each of the pieceMoves of the opposing team's pieces, check to see if the end position is the king's position
        //if any of those are true, it is in Check
        KingFinder kingFinder = new KingFinder(teamColor, board);
        ChessPosition kingPosition = kingFinder.getKing();
        for (int i=1; i<9; i++){
            for (int j=1; j<9; j++){
                ChessPosition searchPos = new ChessPosition(i,j);
                ChessPiece searchPiece = board.getPiece(searchPos);
                if (isDangerous(searchPiece, searchPos, kingPosition, teamColor)){
                    return true;
                }
            }
        }
        return false;
    }



    public boolean canMakeMove(ChessPiece searchPiece, ChessPosition searchPos, TeamColor teamColor){
        if (searchPiece == null){
            return false;
        }
        if (searchPiece.getTeamColor() != teamColor){
            return false;
        }
        Collection<ChessMove> validMoves = validMoves(searchPos);
        if (validMoves.isEmpty()){
            return false;
        }
        return true;
    }
    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    // call isInCheck first
    // then find the King's possible moves and call is in check on each of those moves
    //
    public boolean isInCheckmate(TeamColor teamColor) {
        if (!isInCheck(teamColor)){
            return false;
        }
        for (int i=1; i<9; i++){
            for (int j=1; j<9; j++){
                ChessPosition searchPos = new ChessPosition(i,j);
                ChessPiece searchPiece = board.getPiece(searchPos);
                if (canMakeMove(searchPiece, searchPos, teamColor)){
                    return false;
                }
            }
        }
        return true;
        //call valid moves on every single piece of that color, if it returns null for all, bro is in checkmate
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves while not in check.
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        if (isInCheck(teamColor)){
            return false;
        }
        for (int i=1; i<9; i++){
            for (int j=1; j<9; j++){
                ChessPosition searchPos = new ChessPosition(i,j);
                ChessPiece searchPiece = board.getPiece(searchPos);
                if (canMakeMove(searchPiece, searchPos, teamColor)){
                    return false;
                }
            }
        }
        return true;
    }
    //wohoo done!

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.board = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return board;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessGame chessGame = (ChessGame) o;
        return teamTurn == chessGame.teamTurn && Objects.equals(board, chessGame.board);
    }

    @Override
    public int hashCode() {
        return Objects.hash(teamTurn, board);
    }
}
