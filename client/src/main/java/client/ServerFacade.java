package client;


//This class will simply take requests and return the modified nice error message
// or the correct info

//It will also handle the mapping of chess games to the menu items

import client.requests.CreateGameRequest;
import client.requests.JoinGameRequest;
import client.requests.LoginRequest;
import client.requests.RegisterRequest;
import client.responses.*;
import com.google.gson.Gson;
import ui.EscapeSequences;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;


public class ServerFacade {

    ArrayList<GameData> gameList = new ArrayList<>();
    String serverURL;
    ServerCaller serverCall;

    public ServerFacade(int port, String authToken){
        serverURL = String.format("http://localhost:%d", port);
        serverCall = new ServerCaller(serverURL);
        listGames(authToken);
    }

    public ServerFacade(int port){
        serverURL = String.format("http://localhost:%d", port);
        serverCall = new ServerCaller(serverURL);
    };

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
            listGames(authToken);
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
                gameList = listGamesRes.games();
                return formatGames();
            }
        } catch (ResponseException ex){
            return ex.getMessage();
        }
    }

    private String formatGames(){
        StringBuilder gameListString = new StringBuilder();
        gameListString.append("\n");
        for (int i=0; i < gameList.size(); i++){
            GameData targetGame = gameList.get(i);
            gameListString.append(i+1);
            gameListString.append(") ");
            gameListString.append(targetGame.gameName());
            gameListString.append(" ");
            gameListString.append(EscapeSequences.BLACK_PAWN);
            gameListString.append(targetGame.whiteUsername());
            gameListString.append(" ");
            gameListString.append(EscapeSequences.WHITE_PAWN);
            gameListString.append(targetGame.blackUsername());
            gameListString.append("\n");
        }
        gameListString.append("\n");
        return gameListString.toString();
    }

    //In charge of making sure that gameId is actually valid, return an error message if not
    public JoinGameResponse joinGame(String gameNumberString, String teamColor, String authToken){
        String inputError = "Error: please enter a valid game number";
        int gameNumberInt = 0;
        try{
            gameNumberInt = Integer.parseInt(gameNumberString);
            if (gameNumberInt <= 0 || gameNumberInt > gameList.size()){
                return new JoinGameResponse(inputError);
            }
        } catch (Exception e){
            return new JoinGameResponse(inputError);
        }

        JoinGameRequest body = new JoinGameRequest(gameList.get(gameNumberInt - 1).gameID(), teamColor);
        HttpRequest req = serverCall.prepareRequest("/game", "PUT", body, authToken);
        try {
            HttpResponse<String> res = serverCall.sendRequest(req);
            return new Gson().fromJson(res.body(), JoinGameResponse.class);
        } catch (ResponseException ex){
            return new JoinGameResponse(ex.getMessage());
        }

    }

    public int getGameId(int gameNumber){
        return gameList.get(gameNumber - 1).gameID();
    }

    //This should make sure that a gameId is valid as well
    public String observeGame(String gameId){
        String inputError = "Error: please enter a valid game number";
        int gameNumberInt = 0;
        try{
            gameNumberInt = Integer.parseInt(gameId);
            if (gameNumberInt <= 0 || gameNumberInt > gameList.size()){
                return inputError;
            }
        } catch (Exception e){
            return inputError;
        }
        return null;
    }

    public LogoutResponse logout(String authToken){
        HttpRequest req = serverCall.prepareRequest("/session", "DELETE", null, authToken);
        try {
            HttpResponse<String> res = serverCall.sendRequest(req);
            return new Gson().fromJson(res.body(), LogoutResponse.class);
        } catch (ResponseException ex){
            return new LogoutResponse(ex.getMessage());
        }
    }

    public void clear(){
        HttpRequest req = serverCall.prepareRequest("/db", "DELETE", null, null);
        try {
            HttpResponse<String> res = serverCall.sendRequest(req);
            if (res.statusCode() > 300) {
                System.out.print("ERROR");
            }
        } catch (ResponseException ex){
            System.out.print(ex.getMessage());
        }
    }
}
