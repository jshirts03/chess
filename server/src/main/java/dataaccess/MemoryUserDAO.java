package dataaccess;


import datatypes.UserData;
import java.util.ArrayList;
import java.util.HashSet;

public class MemoryUserDAO implements UserDAO{
    private HashSet<UserData> users;

    public MemoryUserDAO(){
        users = new HashSet<UserData>();
    }

    public void clear(){
        users.clear();
    }
    //insert methods here that manipulate the list of users stored in this DAO object
    //because there no DB, we just create one of these when the server starts up and then it will reset
}
