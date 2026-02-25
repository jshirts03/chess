package dataaccess;

import datatypes.UserData;

public interface UserDAO {
    void clear();
    UserData getUser(String username);
    void createUser(UserData user);
}
