package service;

import dataaccess.DataAccessException;
import dataaccess.MemoryUserDAO;
import dataaccess.UserDAO;
import datatypes.UserData;
import service.requests.RegisterRequest;
import service.responses.RegisterResponse;

public class UserService {
    private UserDAO db;

    public UserService(){
        db = new MemoryUserDAO();
    }

    public void clear(){
        db.clear();
    }

    public RegisterResponse register(RegisterRequest request) throws DataAccessException {
        UserData user = db.getUser(request.username());
        if (user != null){
            throw new DataAccessException("Error: username already taken");
        }
        user = new UserData(request.username(), request.password(), request.email());
        db.createUser(user);
        return new RegisterResponse(user.username(), "");
    }
}
