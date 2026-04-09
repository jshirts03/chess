package client;

import chess.ChessMove;
import chess.ChessPiece;
import chess.ChessPosition;

import java.util.HashMap;
import java.util.Scanner;

public interface GameMenu {


    default boolean verifyCoords(String coords) {
        if (coords.length() != 2){
            System.out.println("Error: invalid coordinates (be sure to do letter first)");
            return false;
        }
        String[] letters = {"a","b","c","d","e","f","g","h"};
        String[] numbers = {"1","2","3","4","5","6","7","8"};
        for (var letter : letters) {
            if (coords.substring(0, 1).equals(letter)) {
                return true;
            }
        }
        for (var number : numbers) {
            if (coords.substring(1,2).equals(number)) {
                return true;
            }
        }
        System.out.println("Error: invalid coordinates (be sure to do letter first)");
        return false;
    }


    default ChessMove createChessMove(String pieceCoords, String moveCoords){
        HashMap<String, Integer> letterToNumber = new HashMap<>();
        letterToNumber.put("a", 1);
        letterToNumber.put("b", 2);
        letterToNumber.put("c", 3);
        letterToNumber.put("d", 4);
        letterToNumber.put("e", 5);
        letterToNumber.put("f", 6);
        letterToNumber.put("g", 7);
        letterToNumber.put("h", 8);
        int coord1 = letterToNumber.get(pieceCoords.substring(0,1));
        int coord2 = Integer.parseInt(pieceCoords.substring(1,2));
        ChessPosition startPos = new ChessPosition(coord2, coord1);
        coord1 = letterToNumber.get(moveCoords.substring(0,1));
        coord2 = Integer.parseInt(moveCoords.substring(1,2));
        ChessPosition endPos = new ChessPosition(coord2, coord1);
        return new ChessMove(startPos, endPos);
    }


    default ChessMove highlightLegalMoves(){
        boolean isValidCoordinates = false;
        String pieceCoords = null;
        Scanner scanner = new Scanner(System.in);
        while (!isValidCoordinates) {
            System.out.print("""
                    Enter coordinates of piece >>> """);
            pieceCoords = scanner.nextLine();
            isValidCoordinates = verifyCoords(pieceCoords);
        }
        return createChessMove(pieceCoords, "a1");
    }
}
