package client;

import client.responses.CreateGameResponse;
import client.responses.JoinGameResponse;
import client.responses.LogoutResponse;
import ui.BoardPrinter;

import java.util.Scanner;

public class PostLoginMenu implements Menu{

    private String authToken;

    PostLoginMenu(String authToken){
        this.authToken = authToken;
        this.serverF = new ServerFacade(8080,authToken);
    }
    boolean shouldContinue = true;
    ServerFacade serverF;


    public void run(){
        while (shouldContinue){
            System.out.print("""
                    \n Welcome Chess Player!
                    1) Help
                    2) Create Game
                    3) List Games
                    4) Play Game
                    5) Observe Game
                    6) Logout
                    Please select a menu number >>> \s""");
            Scanner scanner = new Scanner(System.in);
            String selection = scanner.nextLine();
            selectionHandler(selection);
        }
    }

    public void selectionHandler(String selection){
        int menuNumber = 0;
        try{
            menuNumber = Integer.parseInt(selection);
        } catch (Exception e){
            printInputError();
            return;
        }
        switch (menuNumber){
            case 1:
                System.out.print("""
                        Create game: makes a new chess game for you to join
                        List games: displays a list of all active games
                        Play game: allows you to join an existing game (see list games menu for game number)
                        Observe game: allows you to spectate an existing game (see list games menu for game number)
                        Logout: return to the login menu
                        Valid inputs are (1,2,3,4,5,or 6)""");
                break;
            case 2:
                createGame();
                break;
            case 3:
                listGames();
                break;
            case 4:
                playGame();
                break;
            case 5:
                observeGame();
                break;
            case 6:
                logout();
                shouldContinue = false;
                break;
            default:
                printInputError();
        }

    }

    public void printInputError(){
        System.out.print("""
                Error: Invalid Input
                Please enter a valid number (1,2,3,4,5,6)
                """);
    }

    public String checkForServerErrors(String response){
        if (response.contains("Error")) {
            System.out.print(response);
            if (retry()) {
                return null;
            }
        }
        return response;
    }

    public void createGame(){
        String createRes = null;
        Scanner scanner = new Scanner(System.in);
        while (createRes == null) {
            System.out.print("""
                    Create Game
                    Game Name >>> \s""");
            String gameName = scanner.nextLine();

            createRes = serverF.createGame(gameName, authToken).message();
            if (createRes == null){
                System.out.printf("Successfully created game titled %s \n", gameName);
                createRes = "Success";
            }
            createRes = checkForServerErrors(createRes);
        }
    }

    public void listGames(){
        String listRes = serverF.listGames(authToken);
        listRes = checkForServerErrors(listRes);
        if (listRes != null){
            System.out.print(listRes);
        }
    }

    public void playGame(){
        String teamColor = "BLUE";
        String joinRes = null;
        Scanner scanner = new Scanner(System.in);
        while (joinRes == null) {
            System.out.print("""
                    Play Game
                    Game # >>> \s""");
            String gameNumberString = scanner.nextLine();
            System.out.print("Team Color (WHITE or BLACK) >>> ");
            teamColor = scanner.nextLine();
            JoinGameResponse joinGameRes = serverF.joinGame(gameNumberString, teamColor, authToken);
            if (joinGameRes != null){
                joinRes = joinGameRes.message();
            }
            if (joinRes == null){
                System.out.printf("Successfully joined game #%s \n", gameNumberString);
                joinRes = "Success";
                int gameId = serverF.getGameId(Integer.parseInt(gameNumberString));
                new GamePlayerMenu(gameId, authToken).run();
            }
            joinRes = checkForServerErrors(joinRes);
        }

    }

    public void observeGame(){
        String observeRes = null;
        Scanner scanner = new Scanner(System.in);
        while (observeRes == null) {
            System.out.print("""
                    Observe Game
                    Game # >>> \s""");
            String gameNumberString = scanner.nextLine();
            observeRes = serverF.observeGame(gameNumberString);
            if (observeRes == null){
                observeRes = "Success";
                int gameId = serverF.getGameId(Integer.parseInt(gameNumberString));
                new GameObserverMenu(gameId, authToken);
            }
            observeRes = checkForServerErrors(observeRes);
        }
    }

    public void logout(){
        LogoutResponse logoutRes = serverF.logout(authToken);
        if (logoutRes != null){
            checkForServerErrors(logoutRes.message());
        }
    }




}
