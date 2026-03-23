package client;

import java.util.Scanner;

public class PostLoginMenu implements Menu{

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
                //authToken = loginUser();
                break;
            case 3:
                System.out.println("YAYY list game");
                //authToken = registerUser();
                break;
            case 4:
                System.out.println("YAYYY play game");
                //authToken = "Quit";
                break;
            case 5:
                System.out.println("YAYYY observe game");
                break;
            case 6:
                System.out.println("YAYYY logout");
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

            createRes = serverF.createGame(gameName);
            createRes = checkForServerErrors(createRes);
        }
    }

    public void listGames(){
        String listRes = serverF.listGames();
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
            System.out.print("Team Color (W or B) >>> ");
            String teamColor = scanner.nextLine();
            joinRes = serverF.joinGame(gameNumberString, teamColor);
            joinRes = checkForServerErrors(joinRes);
        }
    }




}
