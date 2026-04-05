package client;

public class GameObserverMenu {
    String authToken;
    int gameId;

    public GameObserverMenu(int gameId, String authToken){
        this.authToken = authToken;
        this.gameId = gameId;
    }
}
