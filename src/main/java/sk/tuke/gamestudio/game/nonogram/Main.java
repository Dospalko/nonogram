package sk.tuke.gamestudio.game.nonogram;

import sk.tuke.gamestudio.game.nonogram.consoleui.ConsoleUI;

import java.io.IOException;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("                                            \n" +
                " _____                                      \n" +
                "|   | | ___  ___  ___  ___  ___  ___  _____ \n" +
                "| | | || . ||   || . || . ||  _|| .'||     |\n" +
                "|_|___||___||_|_||___||_  ||_|  |__,||_|_|_|\n" +
                "                      |___|                 \n" +
                "                                          ");
               System.out.println("Please choose a level (1-5): ");

        Scanner scanner = new Scanner(System.in);
        int level = scanner.nextInt();

        if (level < 1 || level > 5) {
            System.out.println("Invalid level. Please choose a level between 1 and 5: ");
            level = scanner.nextInt();
        }


        var ui = new ConsoleUI(level);
        ui.play();
    }
}
