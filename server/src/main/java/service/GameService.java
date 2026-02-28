package service;

import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import dataaccess.MemoryGameDAO;
import datatypes.GameData;
import service.requests.CreateGameRequest;
import service.responses.ListGameData;
import service.responses.ListGamesResponse;
import service.responses.NewGameResponse;

import java.util.ArrayList;
import java.util.HashSet;

public class GameService {
    private GameDAO db;
    public GameService() {
        db = new MemoryGameDAO();
    }

    public void clear() {
        db.clear();
    }

    public NewGameResponse createGame(CreateGameRequest request) throws DataAccessException{
        if (request.gameName() == null){
            throw new DataAccessException("Error: bad request");
        }
        int gameId = db.createGame(request.gameName());
        return new NewGameResponse(gameId);
    }

    public ListGamesResponse listGames(){
        ArrayList<GameData> games = db.getGames();
        ListGameData[] listedGames = new ListGameData[games.size()];
        for (int i=0; i<games.size(); i++){
            GameData game = games.get(i);
            listedGames[i] = new ListGameData(game.gameID(), game.whiteUsername(), game.blackUsername(), game.gameName());
        }
        return new ListGamesResponse(listedGames);
    }
}
