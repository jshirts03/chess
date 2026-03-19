package client;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PreLoginMenu {
    String authToken = null;
    ServerFacade serverF = new ServerFacade();

    //run will run the input loop to have the menu options
    public String run(){
        while (authToken == null ){
            System.out.print("""
                    Welcome to Chess!
                    1) Help
                    2) Login
                    3) Register
                    4) Quit
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
                authToken = "1234";
                //registerUser();
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

    public boolean retry(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Try again? (Y N) >>> ");
        String retryRes = scanner.nextLine();
        return retryRes.equals("Y");
    }


    public String loginUser(){
        String loginRes = null;
        Scanner scanner = new Scanner(System.in);
        while (loginRes == null){
            System.out.print("""
                    Login
                    Username >>> \s""");
            String username = scanner.nextLine();
            System.out.print("Password >>>  ");
            String password = scanner.nextLine();

            loginRes = serverF.login(username, password);
            if (loginRes.contains("Error")){
                System.out.print(loginRes);
                if (retry()){
                    loginRes = null;
                }
                else{
                    return null;
                }
            }
        }
        return loginRes;
    }

    public String registerUser() {
        String registerRes = null;
        Scanner scanner = new Scanner(System.in);
        while (registerRes == null) {
            System.out.print("""
                    Register
                    Email >>> \s""");
            String email = scanner.nextLine();
            System.out.print("Username >>>  ");
            String username = scanner.nextLine();
            System.out.print("Password >>>  ");
            String password = scanner.nextLine();
            registerRes = serverF.register(email, username, password);
            if (registerRes.contains("Error")) {
                System.out.print(registerRes);
                if (retry()) {
                    registerRes = null;
                } else {
                    return null;
                }
            }
        }
        return registerRes;
    }
}
