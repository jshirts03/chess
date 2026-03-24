package ui;

import static ui.EscapeSequences.*;

public class BoardPrinter {

    private String bw = SET_BG_COLOR_WHITE;

    public static void main(String[] args){
        new BoardPrinter().printWhite();
        new BoardPrinter().printBlack();
    }
    public void printWhite(){
        String[] letters = {"a", "b", "c", "d", "e", "f", "g", "h"};
        String[] numbers = {"8", "7", "6", "5", "4", "3", "2", "1"};
        String[] royalty = {"Q", "K"};
        printBoard(letters, numbers, royalty, "white");
    }

    public void printBlack(){
        String[] letters = {"h", "g", "f", "e", "d", "c", "b", "a"};
        String[] numbers = {"1", "2", "3", "4", "5", "6", "7", "8"};
        String[] royalty = {"K", "Q"};
        printBoard(letters, numbers, royalty, "black");
    }

    public String bwBackground(){
        String currentBw = bw;
        if (bw.equals(SET_BG_COLOR_WHITE)){
            bw = SET_BG_COLOR_BLACK;
        }
        else{
            bw = SET_BG_COLOR_WHITE;
        }
        return currentBw;
    }

    public void printBoard(String[] letters, String[] numbers, String[] royalty, String color){
        StringBuilder builder = new StringBuilder();
        int numberTracker = 0;
        String[] toppieces = {"R", "N", "B", royalty[0], royalty[1], "B", "N", "R"};
        String[] bottompieces = {"R", "N", "B", royalty[0], royalty[1], "B", "N", "R"};
        String newRow = RESET_BG_COLOR + "\n" + SET_BG_COLOR_DARK_GREY;
        builder.append(SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_BLACK + SET_TEXT_BOLD);

        //topRow
        builder.append("   ");
        for (int i=0; i<8; i++){
            builder.append(" ").append(letters[i]).append(" ");
        }
        builder.append("   ");
        builder.append(newRow);

        //first row
        builder.append(" ").append(numbers[numberTracker]).append(" ");
        if (color.equals("white")){
            builder.append(SET_TEXT_COLOR_BLUE);
        } else {
            builder.append(SET_TEXT_COLOR_RED);
        }
        for (int i=0; i<8; i++){
            builder.append(bwBackground()).append(" ").append(toppieces[i]).append(" ");
        }
        builder.append(SET_BG_COLOR_DARK_GREY).append(SET_TEXT_COLOR_BLACK).append(" ").append(numbers[numberTracker]).append(" ");
        builder.append(newRow);

        //pawn row top
        bwBackground();
        numberTracker++;
        builder.append(" ").append(numbers[numberTracker]).append(" ");
        if (color.equals("white")){
            builder.append(SET_TEXT_COLOR_BLUE);
        } else {
            builder.append(SET_TEXT_COLOR_RED);
        }
        for (int i=0; i<8; i++){
            builder.append(bwBackground()).append(" ").append("P").append(" ");
        }
        builder.append(SET_BG_COLOR_DARK_GREY).append(SET_TEXT_COLOR_BLACK).append(" ").append(numbers[numberTracker]).append(" ");
        builder.append(newRow);

        //blank middles
        while (numberTracker < 5){
            bwBackground();
            numberTracker++;
            builder.append(" ").append(numbers[numberTracker]).append(" ");
            for (int i=0; i<8; i++){
                builder.append(bwBackground()).append("   ");
            }
            builder.append(SET_BG_COLOR_DARK_GREY).append(SET_TEXT_COLOR_BLACK).append(" ").append(numbers[numberTracker]).append(" ");
            builder.append(newRow);
        }

        bwBackground();
        numberTracker++;
        builder.append(" ").append(numbers[numberTracker]).append(" ");
        if (color.equals("black")){
            builder.append(SET_TEXT_COLOR_BLUE);
        } else {
            builder.append(SET_TEXT_COLOR_RED);
        }
        for (int i=0; i<8; i++){
            builder.append(bwBackground()).append(" ").append("P").append(" ");
        }
        builder.append(SET_BG_COLOR_DARK_GREY).append(SET_TEXT_COLOR_BLACK).append(" ").append(numbers[numberTracker]).append(" ");
        builder.append(newRow);

        bwBackground();
        numberTracker++;
        builder.append(" ").append(numbers[numberTracker]).append(" ");
        if (color.equals("black")){
            builder.append(SET_TEXT_COLOR_BLUE);
        } else {
            builder.append(SET_TEXT_COLOR_RED);
        }
        for (int i=0; i<8; i++){
            builder.append(bwBackground()).append(" ").append(bottompieces[i]).append(" ");
        }
        builder.append(SET_BG_COLOR_DARK_GREY).append(SET_TEXT_COLOR_BLACK).append(" ").append(numbers[numberTracker]).append(" ");
        builder.append(newRow);

        builder.append("   ");
        for (int i=0; i<8; i++){
            builder.append(" ").append(letters[i]).append(" ");
        }
        builder.append("   ");
        builder.append(newRow);

        builder.append(RESET_BG_COLOR).append(RESET_TEXT_COLOR).append(RESET_TEXT_BOLD_FAINT);
        builder.append("\n");
        System.out.print(builder.toString());
    }

    //pawn row top


}
