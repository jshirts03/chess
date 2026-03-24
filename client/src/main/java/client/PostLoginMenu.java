package client;

import client.responses.CreateGameResponse;
import client.responses.JoinGameResponse;

import java.util.Scanner;

public class PostLoginMenu implements Menu{

    private String authToken;

    PostLoginMenu(String authToken){
        this.authToken = authToken;
    }
    boolean shouldContinue = true;
    ServerFacade serverF = new ServerFacade();

    public void run(){
        while (shouldContinue){
            System.out.print("""
                    Welcome Chess Player!
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
                System.out.println("YAYY help");
                break;
            case 2:
                System.out.println("YAYY create game");
                createGame();
                break;
            case 3:
                System.out.println("YAYY list game");
                listGames();
                break;
            case 4:
                System.out.println("YAYYY play game");
                playGame();
                break;
            case 5:
                System.out.println("YAYYY observe game");
                observeGame();
                break;
            case 6:
                System.out.println("YAYYY logout");
                shouldContinue = false;
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
        String joinRes = null;
        Scanner scanner = new Scanner(System.in);
        while (joinRes == null) {
            System.out.print("""
                    Play Game
                    Game # >>> \s""");
            String gameNumberString = scanner.nextLine();
            System.out.print("Team Color (WHITE or BLACK) >>> ");
            String teamColor = scanner.nextLine();
            JoinGameResponse joinGameRes = serverF.joinGame(gameNumberString, teamColor, authToken);
            if (joinGameRes != null){
                joinRes = joinGameRes.message();
            }
            if (joinRes == null){
                System.out.printf("Successfully joined game #%s \n", gameNumberString);
                joinRes = "Success";
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
            observeRes = serverF.observeGame(gameNumberString, authToken);
            observeRes = checkForServerErrors(observeRes);
        }
    }

    public void logout(String authToken){
        LougoutResponse logoutRes = serverf.logout(authToken);
        checkServerErrors(logoutRes.message());
    }




}
