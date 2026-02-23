package service;

import dataaccess.MemoryAuthDAO;

public class AuthService {
    private MemoryAuthDAO db;
    public AuthService () {
        db = new MemoryAuthDAO();
    }

}
