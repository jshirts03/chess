package dataaccess;

import datatypes.AuthData;
import service.requests.JoinGameRequest;

public interface AuthDAO {
    void clear();
    AuthData createAuth(String username);
    void deleteAuth(String authToken) throws DataAccessException;
    AuthData verifyAuth(String authToken) throws DataAccessException;
    String getUserWithAuth(String authToken);
}
