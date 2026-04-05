package client;

public class GamePlayerMenu {
    WebSocketFacade webSocketF;
    int gameId;
    int authToken;

    //on connection (run), add a
    public GamePlayerMenu(int gameId, int authToken){
        this.gameId = gameId;
        this.authToken = authToken;
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
        //calls websocketF to
        //make move
        //highlight legal moves
        //resign
        //leave
        webSocketF.game

    }
}
