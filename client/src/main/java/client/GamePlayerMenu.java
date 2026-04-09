package client;

import chess.ChessMove;
import chess.ChessPosition;
import ui.BoardPrinter;
import websocket.commands.MakeMoveCommand;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class GamePlayerMenu implements GameMenu{
    BoardPrinter boardPrinter;
    WebSocketFacade webSocketF;
    int gameId;
    String authToken;
    boolean isInGame;

    //on connection (run), add a
    public GamePlayerMenu(int gameId, String authToken){
        this.isInGame = true;
        this.gameId = gameId;
        this.authToken = authToken;
        this.boardPrinter = new BoardPrinter();
        this.webSocketF = new WebSocketFacade(new NotificationHandler(boardPrinter));
    }

    public void run() {
        try {
            webSocketF.connectToGame(authToken, gameId);
        } catch (ResponseException e) {
            System.out.print(e.getMessage());
            return;
        }

        //joins game on the server side
        //server will send a render game message that will render the board (done in the message handler)
        //you don't need to know if it's your turn on the client side. that is stored in the chess game in the server
        //Just loop through the UI options and the server will send back an error message if it's not your turn or if move is invalid
        //this class only will run the

        while (isInGame) {
            System.out.print("""
                    Select an option (enter 1 for menu) >>> 
                    """);
            Scanner scanner = new Scanner(System.in);
            String selection = scanner.nextLine();
            selectionHandler(selection);
        }
    }

    public void selectionHandler(String selection){
        int selectionNumber = 0;
        try{
            selectionNumber = Integer.parseInt(selection);
        } catch (Exception e){
            printInputError();
            return;
        }
        switch (selectionNumber){
            case 1:
                System.out.print("""
                    2) Draw Board: allows you to redraw the current chess board
                    3) Highlight Legal Moves: prompts for a location on the chess board and
                    will print a new board with that piece's legal moves highlighted
                    4) Make Move: prompts for a piece location and the desired move location
                    will make that move if it's your turn and the move is valid
                    5) Resign: allows you to forfeit the game
                    6) Leave: leaves the game and sends you back to the menu""");
                break;
            case 2:
                drawBoard();
                break;
            case 3:
                ChessMove move = highlightLegalMoves();
                boardPrinter.printHighlighted(move.getStartPosition());
                break;
            case 4:
                makeMove();
                break;
            case 5:
                resign();
                break;
            case 6:
                leave();
                isInGame = false;
                break;
            default:
                printInputError();
        }

    }

    public void printInputError(){
        System.out.print("""
                Error: Invalid Input
                Please enter a valid number (1,2,3,4,5,6)
                """);
    }

    public void drawBoard(){
        boardPrinter.loadBoard();
    }

    public void leave(){
        webSocketF.leaveGame(authToken, gameId);
    }

    public void makeMove(){
        boolean isValidCoordinates = false;
        String pieceCoords = null;
        String moveCoords = null;
        Scanner scanner = new Scanner(System.in);
        while (!isValidCoordinates){
            System.out.print("""
                    Enter coordinates of piece >>> """);
            pieceCoords = scanner.nextLine();
            System.out.print("""
                    Enter coordinates of move >>> """);
            moveCoords = scanner.nextLine();
            isValidCoordinates = (verifyCoords(pieceCoords) && verifyCoords(moveCoords));
        }
        ChessMove move = createChessMove(pieceCoords, moveCoords);
        webSocketF.makeMove(authToken, gameId, move);
    }


    public void resign(){
        System.out.print("Are you sure you want to resign? (Y or N) >>>   ");
        Scanner scanner = new Scanner(System.in);
        String selection = scanner.nextLine();
        if (selection.equals("Y")){
            webSocketF.resign(authToken, gameId);
        }
    }

}
