package dataaccess;

import datatypes.AuthData;
import java.util.HashSet;

public class MemoryAuthDAO implements AuthDAO{
    HashSet<AuthData> authSet;
    public MemoryAuthDAO(){
        authSet = new HashSet<AuthData>();
    }

    public void clear(){
        authSet.clear();
    }
}
