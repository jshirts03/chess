package client;


//This class will simply take requests and return the modified nice error message
// or the correct info

//It will also handle the mapping of chess games to the menu items

import client.requests.CreateGameRequest;
import client.requests.LoginRequest;
import client.requests.RegisterRequest;
import client.responses.CreateGameResponse;
import client.responses.ListGamesResponse;
import client.responses.LoginResponse;
import client.responses.RegisterResponse;
import com.google.gson.Gson;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;


public class ServerFacade {

    ArrayList<GameData> gameList = new ArrayList<>();
    ServerCaller serverCall = new ServerCaller("http://localhost:8080");

    public LoginResponse login(String username, String password){
        LoginRequest body = new LoginRequest(username, password);
        HttpRequest req = serverCall.prepareRequest("/session", "POST", body, null);
        try {
            HttpResponse<String> res = serverCall.sendRequest(req);
            return new Gson().fromJson(res.body(), LoginResponse.class);
        } catch (ResponseException ex){
            return new LoginResponse(null, null, ex.getMessage());
        }
    }

    public RegisterResponse register(String email, String username, String password){
        RegisterRequest body = new RegisterRequest(email, username, password);
        HttpRequest req = serverCall.prepareRequest("/user", "POST", body, null);
        try {
            HttpResponse<String> res = serverCall.sendRequest(req);
            return new Gson().fromJson(res.body(), RegisterResponse.class);
        } catch (ResponseException ex){
            return new RegisterResponse(null, null, ex.getMessage());
        }
    }

    public CreateGameResponse createGame(String gameName, String authToken){
        CreateGameRequest body = new CreateGameRequest(gameName);
        HttpRequest req = serverCall.prepareRequest("/game", "POST", body, authToken);
        try {
            HttpResponse<String> res = serverCall.sendRequest(req);
            return new Gson().fromJson(res.body(), CreateGameResponse.class);
        } catch (ResponseException ex){
            return new CreateGameResponse(ex.getMessage());
        }
    }

    //This method will be in charge of keeping record of all of the game mappings to display to the user
    public String listGames(String authToken){
        HttpRequest req = serverCall.prepareRequest("/game", "GET", null, authToken);
        try {
            HttpResponse<String> res = serverCall.sendRequest(req);
            ListGamesResponse listGamesRes = new Gson().fromJson(res.body(), ListGamesResponse.class);
            if (listGamesRes.message() != null){
                return listGamesRes.message();
            }
            else{
                return formatGames(listGamesRes);
            }
        } catch (ResponseException ex){
            return ex.getMessage();
        }
    }

    public String formatGames(ListGamesResponse listGamesRes){
        gameList = new ArrayList<GameData>();
        for (String game : listGamesRes.games()){
            gameList.add(new Gson().fromJson(game, GameData.class));
        }
        StringBuilder gameListString = new StringBuilder();
        for (int i=0; i < gameList.size(); i++){
            GameData targetGame = gameList.get(i);
            gameListString.append(i);
            gameListString.append(") ");
        }
        return gameListString.toString();
    }

    //In charge of making sure that gameId is actually valid, return an error message if not
    public String joinGame(String gameId, String teamColor, String authToken){
        return "Successfully joined";
    }

    //This should make sure that a gameId is valid as well
    public String observeGame(String gameId, String authToken){
        return "Successfully observing";
    }
}
