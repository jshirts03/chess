package client;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PreLoginMenu {
    String authToken = "placeholder";

    //run will run the input loop to have the menu options
    public String run(){
        while (authToken.equals("placeholder")){
            System.out.print("""
                    Welcome to Chess!%n
                    1) Help
                    2) Login
                    3) Register
                    4) Quit%n
                    Please select a menu number >>> """);
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
        if (menuNumber == 1){
            System.out.println("YAYY help");
            //helpUser();
        }
        if (menuNumber == 2){
            System.out.println("YAYY login");
            authToken = "1234";
            //loginUser();
        }
        if (menuNumber == 3){
            System.out.println("YAYY register");
            authToken = "1234";
            //registerUser();
        }
        if (menuNumber == 4){
            System.out.println("Goodbye");
            authToken = null;
        }
    }

    public void printInputError(){
        System.out.print("""
                Error messsage;
                """)
    }
}
