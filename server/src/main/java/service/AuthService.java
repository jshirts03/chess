package service;

import dataaccess.AuthDAO;
import dataaccess.MemoryAuthDAO;

public class AuthService {
    private AuthDAO db;
    public AuthService () {
        db = new MemoryAuthDAO();
    }

    public void clear(){
        db.clear();
    }
}
