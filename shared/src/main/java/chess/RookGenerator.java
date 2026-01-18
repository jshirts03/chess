package chess;

import java.util.Collection;

public class RookGenerator extends MoveGenerator {
    public RookGenerator(ChessBoard board, ChessPosition position){
        super(board, position);
    }

    public Collection<ChessMove> getMoves() {
        ChessPosition mover = myPosition;
        ChessMove nextMove = null;

        //Check front
    }
}
