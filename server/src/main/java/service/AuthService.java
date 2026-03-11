package service;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.MemoryAuthDAO;
import dataaccess.SQLAuthDAO;
import datatypes.AuthData;
import service.responses.AuthResponse;

public class AuthService {
    private AuthDAO db;
    public AuthService () {
        try {
            db = new SQLAuthDAO();
        } catch (DataAccessException e){
            db = new MemoryAuthDAO();
        }

    }

    public void clear() throws DataAccessException{
        db.clear();
    }

    public AuthResponse authorize(String username){
        AuthData auth = db.createAuth(username);
        return new AuthResponse(auth.username(), auth.authToken());
    }


    public void deleteAuth(String authToken) throws DataAccessException {
        db.deleteAuth(authToken);
    }

    public void verifyAuth(String authToken) throws DataAccessException {
        AuthData auth = db.verifyAuth(authToken);
    }

    public String getUserWithAuth(String authtoken){
        return db.getUserWithAuth(authtoken);
    }
}
