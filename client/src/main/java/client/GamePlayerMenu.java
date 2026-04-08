package client;

import ui.BoardPrinter;

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
                    Create game: makes a new chess game for you to join
                    List games: displays a list of all active games
                    Play game: allows you to join an existing game (see list games menu for game number)
                    Observe game: allows you to spectate an existing game (see list games menu for game number)
                    Logout: return to the login menu
                    Valid inputs are (1,2,3,4,5,or 6)""");
                break;
            case 2:
                drawBoard();
                break;
            case 3:
                highlightLegalMoves();
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
}
