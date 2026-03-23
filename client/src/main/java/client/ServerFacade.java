package client;


//This class will simply take requests and return the modified nice error message
// or the correct info

public class ServerFacade {

    public String login(String username, String password){
        return "Error: Login Unsuccessful!";
    }

    public String register(String email, String username, String password){
        return "Successful registration!";
    }

    public String createGame(String gameName){
        return "Successful creation";
    }
}
