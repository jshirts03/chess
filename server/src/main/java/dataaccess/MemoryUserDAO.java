package dataaccess;


import datatypes.UserData;
import java.util.ArrayList;

public class MemoryUserDAO implements UserDAO{
    private ArrayList<UserData> users;

    MemoryUserDAO(){
        users = new ArrayList<UserData>();
    }

    //insert methods here that manipulate the list of users stored in this DAO object
    //because there no DB, we just create one of these when the server starts up and then it will reset
}
