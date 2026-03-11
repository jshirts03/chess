package dataaccess;

import datatypes.UserData;

public interface UserDAO {
    void clear() throws DataAccessException;
    UserData getUser(String username, String password) throws DataAccessException;
    void createUser(UserData user);
}
