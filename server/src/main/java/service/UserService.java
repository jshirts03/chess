package service;

import dataaccess.MemoryUserDAO;
import dataaccess.UserDAO;

public class UserService {
    private UserDAO db;

    public UserService(){
        db = new MemoryUserDAO();
    }

    public void clear(){
        db.clear();
    }
}
