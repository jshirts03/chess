package service;

import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import dataaccess.MemoryGameDAO;

public class GameService {
    private GameDAO db;
    public GameService() {
        db = new MemoryGameDAO();
    }

    public void clear() throws DataAccessException {
        db.clear();
    }
}
