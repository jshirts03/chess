package service;

import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import dataaccess.MemoryGameDAO;
import service.requests.CreateGameRequest;
import service.responses.NewGameResponse;

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
}
