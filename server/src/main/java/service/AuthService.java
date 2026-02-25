package service;

import dataaccess.AuthDAO;
import dataaccess.MemoryAuthDAO;
import datatypes.AuthData;
import service.responses.AuthResponse;

public class AuthService {
    private AuthDAO db;
    public AuthService () {
        db = new MemoryAuthDAO();
    }

    public void clear(){
        db.clear();
    }

    public AuthResponse authorize(String username){
        AuthData auth = db.createAuth(username);
        return new AuthResponse(auth.username(), auth.authToken());
    }
}
