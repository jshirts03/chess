package chess;

import java.util.ArrayList;
import java.util.Collection;

public class PawnGenerator extends MoveGenerator{

    public PawnGenerator(ChessBoard board, ChessPosition position){
        super(board, position);
    }

    public ArrayList<ChessMove> promotionChecker(ChessMove move){
        ArrayList<ChessMove> promotionMoves = new ArrayList<ChessMove>();
        if (myColor == ChessGame.TeamColor.WHITE && move.endPosition.getRow() == 8){
            promotionMoves.add(new ChessMove(myPosition, move.endPosition, ChessPiece.PieceType.ROOK));
            promotionMoves.add(new ChessMove(myPosition, move.endPosition, ChessPiece.PieceType.KNIGHT));
            promotionMoves.add(new ChessMove(myPosition, move.endPosition, ChessPiece.PieceType.BISHOP));
            promotionMoves.add(new ChessMove(myPosition, move.endPosition, ChessPiece.PieceType.QUEEN));
        }
        if (myColor == ChessGame.TeamColor.BLACK && move.endPosition.getRow() == 1){
            promotionMoves.add(new ChessMove(myPosition, move.endPosition, ChessPiece.PieceType.ROOK));
            promotionMoves.add(new ChessMove(myPosition, move.endPosition, ChessPiece.PieceType.KNIGHT));
            promotionMoves.add(new ChessMove(myPosition, move.endPosition, ChessPiece.PieceType.BISHOP));
            promotionMoves.add(new ChessMove(myPosition, move.endPosition, ChessPiece.PieceType.QUEEN));
        }
        return promotionMoves;
    }

    //This overrides the checkForward function because Pawns cannot move on top of opposing pieces that are in front of them
    @Override
    public ChessMove checkForward(ChessPosition mover){
        ChessPosition forwardPosition = null;
        if (myColor == ChessGame.TeamColor.WHITE){
            forwardPosition = new ChessPosition(mover.getRow()+1, mover.getColumn());
        }
        else{
            forwardPosition = new ChessPosition(mover.getRow()-1, mover.getColumn());
        }
        if (forwardPosition.getRow() < 1 || forwardPosition.getRow() > 8){
            return null;
        }
        if (forwardPosition.getColumn() < 1 || forwardPosition.getColumn() > 8){
            return null;
        }
        if (board.getPiece(forwardPosition) != null){
            return null;
        }

        return new ChessMove(myPosition, forwardPosition);
    }

    //Ovverride because it can only move diagonally if it is killing an enemy pawn
    @Override
    public ChessMove checkDiagonalUpRight(ChessPosition mover){
        ChessPosition diagonalPosition = null;
        if (myColor == ChessGame.TeamColor.WHITE){
            diagonalPosition = new ChessPosition(mover.getRow()+1, mover.getColumn()+1);
        }
        else{
            diagonalPosition = new ChessPosition(mover.getRow()-1, mover.getColumn()-1);
        }
        if (diagonalPosition.getRow() < 1 || diagonalPosition.getRow() > 8){
            return null;
        }
        if (diagonalPosition.getColumn() < 1 || diagonalPosition.getColumn() > 8){
            return null;
        }
        if (board.getPiece(diagonalPosition) == null){
            return null;
        }
        if (board.getPiece(diagonalPosition).getTeamColor() == myColor){
            return null;
        }
        return new ChessMove(myPosition, diagonalPosition);
    }

    //Override because pawns can only move diagonal if they are killing enemy piece
    @Override
    public ChessMove checkDiagonalUpLeft(ChessPosition mover){
        ChessPosition diagonalPosition = null;
        if (myColor == ChessGame.TeamColor.WHITE){
            diagonalPosition = new ChessPosition(mover.getRow()+1, mover.getColumn()-1);
        }
        else{
            diagonalPosition = new ChessPosition(mover.getRow()-1, mover.getColumn()+1);
        }
        if (diagonalPosition.getRow() < 1 || diagonalPosition.getRow() > 8){
            return null;
        }
        if (diagonalPosition.getColumn() < 1 || diagonalPosition.getColumn() > 8){
            return null;
        }
        if (board.getPiece(diagonalPosition) == null){
            return null;
        }
        if (board.getPiece(diagonalPosition).getTeamColor() == myColor){
            return null;
        }
        return new ChessMove(myPosition, diagonalPosition);
    }




    // Generate moves
    Collection<ChessMove> getMoves(){
        ChessPosition mover = myPosition;
        ArrayList<ChessMove> promotionMoves = null;

        //Checks the diagonals, checking for promotion
        ChessMove nextMove = checkDiagonalUpLeft(mover);
        if (nextMove != null){
            promotionMoves = promotionChecker(nextMove);
            if (!promotionMoves.isEmpty()){
                moves.addAll(promotionMoves);
            }
            else{
                moves.add(nextMove);
            }
        }

        nextMove = checkDiagonalUpRight(mover);
        if (nextMove != null){
            promotionMoves = promotionChecker(nextMove);
            if (!promotionMoves.isEmpty()){
                moves.addAll(promotionMoves);
            }
            else{
                moves.add(nextMove);
            }
        }

        //Checks 1 forward, checking for promotion
        nextMove = checkForward(mover);
        if (nextMove != null){
            promotionMoves = promotionChecker(nextMove);
            if (!promotionMoves.isEmpty()){
                moves.addAll(promotionMoves);
            }
            else{
                moves.add(nextMove);
            }
        }
        else{
            return moves;
        }

        //If it's in starting row can move 2 forward
        if (myColor == ChessGame.TeamColor.WHITE && myPosition.getRow()==2){
            mover = new ChessPosition(mover.getRow()+1, mover.getColumn());
            nextMove = checkForward(mover);
            if (nextMove != null){
                moves.add(nextMove);
            }
        }
        if (myColor == ChessGame.TeamColor.BLACK && myPosition.getRow()==7){
            mover = new ChessPosition(mover.getRow()-1, mover.getColumn());
            nextMove = checkForward(mover);
            if (nextMove != null){
                moves.add(nextMove);
            }
        }


        return moves;
    }
    //Checks forward
    //Generator function that checks 1 ahead
    //Use while loops to continue in that direction, calling over and over again until it hits an invalid move

    //
}
