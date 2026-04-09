package client;

import chess.ChessMove;
import chess.ChessPosition;
import ui.BoardPrinter;
import websocket.commands.MakeMoveCommand;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class GamePlayerMenu {
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
                    [ACTIONS]
                    1) Help
                    2) Redraw Board
                    3) Highlight Legal Moves
                    4) Make Move
                    5) Resign
                    6) Leave
                    Select an option >>> 
                    """);
            Scanner scanner = new Scanner(System.in);
            String selection = scanner.nextLine();
            selectionHandler(selection);
        }
    }

    public void selectionHandler(String selection){
        int menuNumber = 0;
        try{
            menuNumber = Integer.parseInt(selection);
        } catch (Exception e){
            printInputError();
            return;
        }
        switch (menuNumber){
            case 1:
                System.out.print("""
                    1) Draw Board: allows you to redraw the current chess board
                    2) Highlight Legal Moves: prompts for a location on the chess board and
                    will print a new board with that piece's legal moves highlighted
                    3) Make Move: prompts for a piece location and the desired move location
                    will make that move if it's your turn and the move is valid
                    4) Resign: allows you to forfeit the game
                    5) Leave: leaves the game and sends you back to the menu""");
                break;
            case 2:
                drawBoard();
                break;
            case 3:
                //highlightLegalMoves();
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


    public boolean verifyCoords(String coords) {
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

    public ChessMove createChessMove(String pieceCoords, String moveCoords){
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


    public void resign(){
        System.out.print("Are you sure you want to resign? (Y or N) >>>   ");
        Scanner scanner = new Scanner(System.in);
        String selection = scanner.nextLine();
        if (selection.equals("Y")){
            webSocketF.resign(authToken, gameId);
        }
    }

        //help
        //prints help instructions

        //redraw board
        //calls the notificationHandler to print the board (done in the BoardPrinter)

        //make move
        //calls the webSocket Facade to make a move with the coordinates
        //notificationHandler will send back an error message if invalid move or if not their turn

        //highlight legal moves
        //calls the notificationHandler to print the board, highlighting the legal moves (done in the BoardPrinter)

        //resign
        //calls the websocketFacade to resign the game, does not leave the gamePlayer Menu

        //leave
        //calls the websocketFacade to leave the game, closes websocket connection
        //exit gamePlayer menu


    //Questions for TA
    //what is the onConnect method for??? if theres a connect message sent by the client?
    //how to debug websocket connections
    //look over my setup in WebsocketFacade and WebsocketHandler
    //look over my sessions hashmap data structure

}
