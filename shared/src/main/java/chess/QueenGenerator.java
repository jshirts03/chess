package chess;

import java.util.ArrayList;
import java.util.Collection;

public class QueenGenerator extends MoveGenerator{
    public QueenGenerator(ChessBoard board, ChessPosition position){
        super(board, position);
    }

    ArrayList<ChessMove> getMoves(){
        RookGenerator rookGen = new RookGenerator(board,myPosition);
        ArrayList<ChessMove> moves = new ArrayList<ChessMove>(rookGen.getMoves());

        BishopGenerator bishopGen = new BishopGenerator(board, myPosition);
        moves.addAll(bishopGen.getMoves());

        return moves;
    }
}
