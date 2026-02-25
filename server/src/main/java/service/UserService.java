package service;

import dataaccess.DataAccessException;
import dataaccess.MemoryUserDAO;
import dataaccess.UserDAO;
import datatypes.UserData;
import service.requests.LoginRequest;
import service.requests.RegisterRequest;
import service.responses.LoginResponse;
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
        if (request.username() == null || request.password() == null || request.email() == null){
            throw new DataAccessException("Error: bad request");
        }
        UserData user = db.getUser(request.username());
        if (user != null){
            throw new DataAccessException("Error: username already taken");
        }
        user = new UserData(request.username(), request.password(), request.email());
        db.createUser(user);
        return new RegisterResponse(user.username(), "");
    }

    public LoginResponse login(LoginRequest request) throws DataAccessException {
        if (request.username() == null || request.password() == null){
            throw new DataAccessException("Error: bad request");
        }
        UserData user = db.getUser(request.username());
        if (user == null || !user.password().equals(request.password())){
            throw new DataAccessException("Error: unauthorized");
        }
        return new LoginResponse(user.username(), "");
    }
}
