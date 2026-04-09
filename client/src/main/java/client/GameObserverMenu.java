package client;

import ui.BoardPrinter;

import java.util.Scanner;

public class GameObserverMenu {
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
                    Type QUIT to stop observing >>>  
                    """);
            Scanner scanner = new Scanner(System.in);
            String selection = scanner.nextLine();
            selectionHandler(selection);
        }
    }

    public void selectionHandler(String selection){
        if (selection.equals("QUIT")){
            shouldQuit = true;
        }
        else{
            System.out.print("Error: invalid entry");
        }
    }
}
