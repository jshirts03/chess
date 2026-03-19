package client;

import java.util.Scanner;

public class PostLoginMenu {

    boolean shouldContinue = true;

    public String run(){
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
        return authToken;
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
                System.out.println("YAYY login");
                authToken = loginUser();
                break;
            case 3:
                System.out.println("YAYY register");
                authToken = registerUser();
                break;
            case 4:
                System.out.println("Goodbye");
                authToken = "Quit";
                break;
            default:
                printInputError();
        }

    }

    public void printInputError(){
        System.out.print("""
                Error: Invalid Input
                Please enter a valid number (1,2,3,4)
                """);
    }
}
