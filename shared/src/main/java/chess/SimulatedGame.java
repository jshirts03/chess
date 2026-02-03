package chess;


import java.util.Collection;

// this class will create a simulated game, with a deep copy of the board
//reset board method that sets the simBoard variable to be a copy of the real GameBoard
//makeMove, same inherited function
//isInCheck, same inherited function
public class SimulatedGame extends ChessGame{
    private ChessBoard simBoard;
    private ChessBoard board;
    public SimulatedGame(ChessBoard board){
        this.board = board;
        resetBoard();
    }
    //Constructor - the same as a ChessGame, inherit everything

    public void resetBoard(){
        simBoard = this.board.clone();
    }

    public void simulateMove(ChessMove move){
        ChessPiece piece = simBoard.getPiece(move.getStartPosition());
        simBoard.addPiece(move.getEndPosition(), piece);
        simBoard.addPiece(move.getStartPosition(), null);
    }

    @Override
    public boolean isInCheck(TeamColor teamColor) {
        //find the king
        //for each of the pieceMoves of the opposing team's pieces, check to see if the end position is the king's position
        //if any of those are true, it is in Check
        KingFinder kingFinder = new KingFinder(teamColor, simBoard);
        ChessPosition kingPosition = kingFinder.getKing();
        for (int i=1; i<9; i++){
            for (int j=1; j<9; j++){
                ChessPosition searchPos = new ChessPosition(i,j);
                ChessPiece searchPiece = simBoard.getPiece(searchPos);
                if (isDangerous(searchPiece, searchPos, kingPosition, teamColor)){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean isDangerous(ChessPiece searchPiece, ChessPosition searchPos, ChessPosition kingPosition, TeamColor teamColor){
        if (searchPiece == null){
            return false;
        }
        if (searchPiece.getTeamColor() == teamColor){
            return false;
        }
        Collection<ChessMove> moves = searchPiece.pieceMoves(simBoard, searchPos);
        for (ChessMove move : moves){
            if (move.getEndPosition().equals(kingPosition)){
                return true;
            }
        }
        return false;
    }
}
