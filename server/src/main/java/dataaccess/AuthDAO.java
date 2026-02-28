package dataaccess;

import datatypes.AuthData;

public interface AuthDAO {
    void clear();
    AuthData createAuth(String username);
    void deleteAuth(String authToken) throws DataAccessException;
}
