package client;

import chess.ChessMove;
import ui.BoardPrinter;

import java.util.Scanner;

public class GameObserverMenu implements GameMenu{
    String authToken;
    int gameId;
    WebSocketFacade webSocketF;
    BoardPrinter boardPrinter;
    boolean shouldQuit;

    public GameObserverMenu(int gameId, String authToken){
        this.shouldQuit = false;
        this.authToken = authToken;
        this.gameId = gameId;
        this.boardPrinter = new BoardPrinter();
        this.webSocketF = new WebSocketFacade(new NotificationHandler(boardPrinter));
    }

    public void run(){
        try {
            webSocketF.connectToGame(authToken, gameId);
        } catch (ResponseException e) {
            System.out.print(e.getMessage());
            return;
        }
        while (!shouldQuit){
            System.out.print("""
                    Enter selection (type 1 for menu) >>>  
                    """);
            Scanner scanner = new Scanner(System.in);
            String selection = scanner.nextLine();
            selectionHandler(selection);
        }
    }

    public void selectionHandler(String selection){
        switch (selection){
            case "1":
                System.out.print("""
                        [ACTIONS]
                        1) Help
                        2) Redraw Board: will reprint the current game board to your screen, from the perspective of white.
                        3) Highlight Legal Moves: prompts you for the location of a chess piece, and if it exists
                        will redraw the current board with that piece's legal moves highlighted.
                        4) Stop observing game: takes you back to the main menu""");
                break;

            case "2":
                boardPrinter.loadBoard();
                break;

            case "3":
                ChessMove move = highlightLegalMoves();
                boardPrinter.printHighlighted(move.getStartPosition());
                break;

            case "4":
                shouldQuit = true;
                break;

            default:
                System.out.print("Error: invalid entry");

        }
    }

}
