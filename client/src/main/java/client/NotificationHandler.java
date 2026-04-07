package client;


import ui.BoardPrinter;

// this class is in charge of printing out notifications or errors to the console
public class NotificationHandler {

    BoardPrinter boardPrinter;

    public NotificationHandler(BoardPrinter boardPrinter){
        this.boardPrinter = boardPrinter;
    }

    //when a notification is received, this class will decide if it's a load_game, error, or notification
    //if it is a load_game, then it will call the board printer to print the board contained in the load_game message
    //the boardPrinter will store this game locally, allowing it to reprint whenever it wants

    //so reprinting the board will call print board on the notification handler
}
