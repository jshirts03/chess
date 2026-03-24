package client;

import java.util.Scanner;

public interface Menu {

    public default boolean retry(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("   Try again? (Y N) >>> ");
        String retryRes = scanner.nextLine();
        return retryRes.equals("Y");
    }
}
