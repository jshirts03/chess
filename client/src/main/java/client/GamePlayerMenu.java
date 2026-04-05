package client;

public class GamePlayerMenu {
    NotificationHandler notificationHandler;
    WebSocketFacade webSocketF;
    int gameId;
    int authToken;

    //on connection (run), add a
    public GamePlayerMenu(int gameId, int authToken){
        this.gameId = gameId;
        this.authToken = authToken;
        this.notificationHandler = new NotificationHandler();
        this.webSocketF = new WebSocketFacade(notificationHandler);
    }

    public void run(){
        webSocketF.joinGame(gameId, authToken);
        //joins game on the server side
        //server will send a render game message that will render the board (done in the message handler)
        //you don't need to know if it's your turn on the client side. that is stored in the chess game in the server
        //Just loop through the UI options and the server will send back an error message if it's not your turn or if move is invalid
        //this class only will run the

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


    }
}
