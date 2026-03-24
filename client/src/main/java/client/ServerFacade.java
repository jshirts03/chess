package client;


//This class will simply take requests and return the modified nice error message
// or the correct info

//It will also handle the mapping of chess games to the menu items

import client.requests.LoginRequest;
import client.responses.LoginResponse;
import com.google.gson.Gson;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashSet;

public class ServerFacade {

    HashSet<Integer> gameMap = new HashSet<>();
    ServerCaller serverCall = new ServerCaller("http://localhost:8080");

    public LoginResponse login(String username, String password){
        LoginRequest body = new LoginRequest(username, password);
        HttpRequest req = serverCall.prepareRequest("/session", "POST", body);
        try {
            HttpResponse<String> res = serverCall.sendRequest(req);
            return new Gson().fromJson(res.body(), LoginResponse.class);
        } catch (ResponseException ex){
            return new LoginResponse(null, null, ex.getMessage());
        }
    }

    public RegisterResponse register(String email, String username, String password){
        ;
    }

    public String createGame(String gameName){
        return "Successful creation";
    }

    //This method will be in charge of keeping record of all of the game mappings to display to the user
    public String listGames(){
        return "GAME 1";
    }

    //In charge of making sure that gameId is actually valid, return an error message if not
    public String joinGame(String gameId, String teamColor){
        return "Successfully joined";
    }

    //This should make sure that a gameId is valid as well
    public String observeGame(String gameId){
        return "Successfully observing";
    }
}
