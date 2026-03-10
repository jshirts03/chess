package dataaccess;

import datatypes.UserData;

public class SQLUserDAO extends UserDAO{

    public SQLUserDAO() throws DataAccessException{
        DatabaseManager.createDatabase();
        DatabaseManager.createTable("user");
    }

    public void clear(){};
    public UserData getUser(String username){};
    public void createUser(UserData user){};
}
