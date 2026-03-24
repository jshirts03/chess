package client;

import chess.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Locale;

//Client main will direct the menu with a while true input loop that renders the correct menu

//Prelogin class that has a run method with a while true input loop that executes the prelogin menu operations
//Quit method exits the loop

//Postlogin class that has a run method with a while true input loop that executes the postlogin menu options
//Logout method exits input loop and

// userinfo = johnny
// while UserInfo is not null:
//  string UserInfo = prelogin.run();  method ends while loop when quit option selected, or
//  if userInfo is not null:
//      UserInfo = postlogin.run(userInfo)  method ends while loop when logout method selected (returns userInfo of null)


public class ClientMain {

    public static void main(String[] args) {
        String authToken = null;
        while (authToken == null) {
            authToken = new PreLoginMenu().run();
            if (!authToken.equals("Quit")) {
                new PostLoginMenu(authToken).run();
                authToken = null;
            }
        }
    }
}
