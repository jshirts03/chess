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

    //This method will be in charge of keeping record of all of the game mappings to display to the user
    public String listGames(){
        return "GAME 1";
    }

    //In charge of making sure that gameId is actually valid, return an error message if not
    public String joinGame(String gameId, String teamColor){
        return "Successfully joined";
    }

    //This should make sure that a gameId is valid as well
    public String observeGame(String gameId){
        return "Successfully observing";
    }
}
