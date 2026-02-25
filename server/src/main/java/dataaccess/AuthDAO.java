package dataaccess;

import datatypes.AuthData;

public interface AuthDAO {
    void clear();
    AuthData createAuth(String username);
}
