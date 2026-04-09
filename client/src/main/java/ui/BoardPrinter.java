package ui;

import chess.*;

import java.util.ArrayList;
import java.util.Collection;

import static ui.EscapeSequences.*;

public class BoardPrinter {

    private String bw = SET_BG_COLOR_WHITE;
    private ChessGame currentGame;
    private ChessBoard board;
    private ChessGame.TeamColor teamColor;
    private String newRow = RESET_BG_COLOR + "\n" + SET_BG_COLOR_DARK_GREY;
    private Collection<ChessMove> validMoves;

    public static void main(String[] args){
        BoardPrinter printer = new BoardPrinter();
        printer.setCurrentGame(new ChessGame());
        printer.setTeamColor(ChessGame.TeamColor.WHITE);
        printer.loadBoard();
        printer.setTeamColor(ChessGame.TeamColor.BLACK);
        printer.loadBoard();
    }

    public void setCurrentGame(ChessGame game){
        currentGame = game;
        board = currentGame.getBoard();
    }

    public void setTeamColor(ChessGame.TeamColor teamColor){
        this.teamColor = teamColor;
    }

    public ChessGame.TeamColor getTeamColor(){
        return teamColor;
    };

    public ChessPiece getChessPiece(ChessPosition position){
        return board.getPiece(position);
    }
    public void loadBoard(){
        if (teamColor.equals(ChessGame.TeamColor.BLACK)){
            printBlack();
        }
        else{
            printWhite();
        }
    }

    public void printHighlighted(ChessPosition startPos){
        ChessPiece movingPiece = board.getPiece(startPos);
        if (movingPiece != null){
            validMoves = currentGame.validMoves(startPos);
            for (ChessMove move : validMoves){
                if (move.getPromotionPiece() != null){
                    eliminateDuplicates(move.getEndPosition());
                    validMoves.add(move);
                }
            }
            loadBoard();
            validMoves = null;
        }
        else{
            System.out.print("Error: no piece can be found");
        }

    }

    public void eliminateDuplicates(ChessPosition position){
        for (ChessMove move: validMoves){
            if (move.getEndPosition().equals(position)){
                validMoves.remove(move);
            }
        }
    }

    public void printWhite(){
        String[] letters = {"a", "b", "c", "d", "e", "f", "g", "h"};
        String[] numbers = {"8", "7", "6", "5", "4", "3", "2", "1"};
        printBoard(letters, numbers);
    }

    public void printBlack(){
        String[] letters = {"h", "g", "f", "e", "d", "c", "b", "a"};
        String[] numbers = {"1", "2", "3", "4", "5", "6", "7", "8"};
        printBoard(letters, numbers);
    }

    public String bwBackground(){
        String currentBw = bw;
        if (bw.equals(SET_BG_COLOR_WHITE)){
            bw = SET_BG_COLOR_BLACK;
        }
        else{
            bw = SET_BG_COLOR_WHITE;
        }
        return currentBw;
    }

    public void printBoard(String[] letters, String[] numbers) {
        StringBuilder builder = new StringBuilder();
        builder.append(SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_BLACK + SET_TEXT_BOLD);

        builder.append("   ");
        for (int i = 0; i < 8; i++) {
            builder.append(" ").append(letters[i]).append(" ");
        }
        builder.append("   ");
        builder.append(newRow);

        if (teamColor.equals(ChessGame.TeamColor.BLACK)){
            builder.append(blackHelper(numbers));
        }
        else{
            builder.append(whiteHelper(numbers));
        }

        builder.append("   ");
        for (int i = 0; i < 8; i++) {
            builder.append(" ").append(letters[i]).append(" ");
        }
        builder.append("   ");
        builder.append(newRow);

        builder.append(RESET_BG_COLOR).append(RESET_TEXT_COLOR).append(RESET_TEXT_BOLD_FAINT);

        if (currentGame.getTeamTurn().equals(ChessGame.TeamColor.WHITE)){
            builder.append("It is WHITE player's turn");
        }
        else{
            builder.append("It is BLACK player's turn");
        }
        builder.append("\n");
        builder.append("------------------------------");
        builder.append("\n");


        System.out.print(builder.toString());
    }


    public String evaluateSpace(int x, int y){
        ChessPosition position = new ChessPosition(x,y);
        ChessPiece piece = board.getPiece(position);
        if (validMoves != null){
            return evaluateHighlight(position, piece);
        }
        if (piece != null){
            return evaluatePieceColor(piece) + evaluatePieceType(piece);
        }
        else{
            return "   ";
        }
    }

    public String evaluateHighlight(ChessPosition position, ChessPiece piece){
        StringBuilder pieceBuilder = new StringBuilder();
        for (ChessMove move : validMoves){
            if (position.equals(move.getEndPosition())){
                if (bw.equals(SET_BG_COLOR_WHITE)){
                    pieceBuilder.append(SET_BG_COLOR_DARK_GREEN);
                }
                else {
                    pieceBuilder.append(SET_BG_COLOR_GREEN);
                }
                if (piece != null){
                    pieceBuilder.append(SET_TEXT_COLOR_BLACK);
                    pieceBuilder.append(evaluatePieceType(piece));
                }
                else{
                    pieceBuilder.append("   ");
                }
            }
            if (position.equals(move.getStartPosition())){
                pieceBuilder.append(SET_BG_COLOR_YELLOW);
                pieceBuilder.append(SET_TEXT_COLOR_BLACK);
                pieceBuilder.append(evaluatePieceType(piece));
                break;
            }
        }
        if (pieceBuilder.isEmpty() && piece != null){
            pieceBuilder.append(evaluatePieceColor(piece));
            pieceBuilder.append(evaluatePieceType(piece));
        }
        if (pieceBuilder.isEmpty()){
            pieceBuilder.append("   ");
        }
        return pieceBuilder.toString();
    }

    public String evaluatePieceType(ChessPiece piece){
        switch (piece.getPieceType()){
            case ChessPiece.PieceType.KING:
                return " K ";
            case ChessPiece.PieceType.QUEEN:
                return " Q ";
            case ChessPiece.PieceType.BISHOP:
                return " B ";
            case ChessPiece.PieceType.ROOK:
                return " R ";
            case ChessPiece.PieceType.KNIGHT:
                return " N ";
            case ChessPiece.PieceType.PAWN:
                return " P ";
        }
        return "   ";
    }

    public String evaluatePieceColor(ChessPiece piece){
        ChessGame.TeamColor color = piece.getTeamColor();
        if (color.equals(ChessGame.TeamColor.BLACK)){
            return SET_TEXT_COLOR_BLUE;
        } else {
            return SET_TEXT_COLOR_RED;
        }
    }

    public String blackHelper(String[] numbers){
        StringBuilder builder = new StringBuilder();
        int numberTracker = 0;
        for (int x = 1; x < 9; x++) {
            builder.append(" ").append(numbers[numberTracker]).append(" ");
            for (int y = 8; y > 0; y--) {
                builder.append(bwBackground());
                builder.append(evaluateSpace(x, y));
            }
            builder.append(SET_BG_COLOR_DARK_GREY).append(SET_TEXT_COLOR_BLACK).append(" ").append(numbers[numberTracker]).append(" ");
            builder.append(newRow);
            numberTracker++;
            bwBackground();
        }
        return builder.toString();
    }

    public String whiteHelper(String[] numbers){
        StringBuilder builder = new StringBuilder();
        int numberTracker = 0;
        for (int x = 8; x > 0; x--) {
            builder.append(" ").append(numbers[numberTracker]).append(" ");
            for (int y = 1; y < 9; y++) {
                builder.append(bwBackground());
                builder.append(evaluateSpace(x, y));
            }
            builder.append(SET_BG_COLOR_DARK_GREY).append(SET_TEXT_COLOR_BLACK).append(" ").append(numbers[numberTracker]).append(" ");
            builder.append(newRow);
            numberTracker++;
            bwBackground();
        }
        return builder.toString();
    }

}
