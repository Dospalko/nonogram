package sk.tuke.gamestudio.game.nonogram.consoleui;

import org.springframework.beans.factory.annotation.Autowired;
import sk.tuke.gamestudio.game.nonogram.core.*;
import sk.tuke.gamestudio.game.nonogram.entity.Comment;
import sk.tuke.gamestudio.game.nonogram.entity.Rating;
import sk.tuke.gamestudio.game.nonogram.entity.Score;
import sk.tuke.gamestudio.game.nonogram.service.comment.CommentService;
import sk.tuke.gamestudio.game.nonogram.service.rating.RatingService;
import sk.tuke.gamestudio.game.nonogram.service.score.ScoreService;

import java.io.FileNotFoundException;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static sk.tuke.gamestudio.game.nonogram.core.TileState.*;


public class ConsoleUI {
    private final PlayingField playingField;
    private final SolvedField solvedField;
    private final HintField verticalHint;
    private final HintField horizontalHint;
    @Autowired
    private ScoreService scoreService;
    @Autowired
    private RatingService ratingService;
    @Autowired
    private CommentService commentService;


    private final Scanner scanner = new Scanner(System.in);
    private static final Pattern commandPatternForColor = Pattern.compile("([C])([ ])([1-9]+)([ ])([1-9]+)([ ])([A-Z]+)");
    private static final Pattern commandPatternForMark = Pattern.compile("([M])([ ])([1-9]+)([ ])([1-9]+)");

    private final long timeAtBegin;
    private static final int TIME_LIMIT_IN_MINUTES = 5; // 5 minutes time limit
    private String username;
    private final int[][] map;
    private int steps = 0;
    private int fieldNumber;

    public ConsoleUI(int fieldNumber) throws FileNotFoundException {
        this.map = new int[][]{
                {8, 8, 4, 4},
                {10, 6, 7, 6},
                {5, 7, 7, 5},
                {4, 4, 2, 2},
                {16, 12, 11, 10},
        };
        this.fieldNumber = fieldNumber;
        timeAtBegin = System.currentTimeMillis();
        int[] fieldData = map[fieldNumber - 1];
        this.solvedField = new SolvedField(fieldData[0], fieldData[1], fieldNumber);
        this.playingField = new PlayingField(fieldData[0], fieldData[1]);
        this.verticalHint = new HintField(fieldData[0], fieldData[2], fieldNumber, true);
        this.horizontalHint = new HintField(fieldData[1], fieldData[3], fieldNumber, false);


    }


    public void play() {
        for (int i = 0; i < 101; i++) {
            System.out.print("\rLoading: " + i + "%");
            try {
                Thread.sleep(25);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("\n");
        System.out.println("Try to beat: \n ");
        printTopScore();
        System.out.print("\nChoose your username: ");

        username = scanner.nextLine();
        long timeAtEnd = System.currentTimeMillis();
        long timeSpentInMillis = timeAtEnd - timeAtBegin;
        long timeSpentInSeconds = TimeUnit.MILLISECONDS.toSeconds(timeSpentInMillis);

        int score = (int) Math.max(0, 360 - timeSpentInSeconds);
        do {

            System.out.println("======================================");
            System.out.println("|       Nonogram commands            |");
            System.out.println("======================================");
            System.out.println("| c (row) (col) (color) - color tile  |");
            System.out.println("| m (row) (col) - mark tile           |");
            System.out.println("| s - show result                     |");
            System.out.println("| x - exit                            |");
            System.out.println("======================================");
            System.out.println("|          Available Colors           |");
            System.out.println("======================================");
            System.out.println("| white | black | red | blue | yellow |");
            System.out.println("| purple | green | gray | orange      |");
            System.out.println("======================================");
            printHorizontalHint();
            printVerticalHint();
            printTime();
            handleInput();
            steps++;
        } while (playingField.getState() == FieldState.PLAYING);
        printHorizontalHint();
        printVerticalHint();
        if (playingField.getState() == FieldState.SOLVED) {
            scoreService.addScore(new Score(username, "nonogram", score, new Date()));
            printWin();
            System.out.println("Congratulations, you won!");
            System.out.println("Steps: " + steps);
            System.out.println("Do you want to leave a comment?\n");
            System.out.println("If you do press 1 if you dont press 0");
            var scan1 = scanner.nextLine();
            int number1 = Integer.parseInt(scan1);
            if (number1 == 1) {
                addComment();
                printComments();
            } else if (number1 == 0) {
                System.exit(0);
            } else {
                System.out.println("Wrong input!");
            }
            System.out.println("Do you want to rate a game?\n");
            System.out.println("If you do press 1 id you dont press 0");
            var scan = scanner.nextLine();
            int number = Integer.parseInt(scan);
            if (number == 1) {
                addRate();
                printRatings();
            } else if (number == 0) {
                System.exit(0);
            } else {
                System.out.println("Wrong input!");
            }

        } else {
            lostart();
            addRate();
            addComment();
            System.out.println("Do you want to see comments and ratings? \n");
            System.out.println("If you do press 1 id you dont press 0");
            var scan = scanner.nextLine();
            int number = Integer.parseInt(scan);
            if (number == 1) {
                printRatings();
                printComments();
            } else if (number == 0) {
                System.exit(0);
            } else {
                System.out.println("Wrong input!");
            }
        }
    }

    private void printHorizontalHint() {
        for (int col = 0; col < horizontalHint.getCol(); col++) {
            printSpace();
            for (int row = horizontalHint.getRow() - 1; row >= 0; row--) {
                Tile tile = horizontalHint.getTile(row, col);
                if (tile instanceof Hint) {
                    Hint hint = (Hint) tile;
                    if (hint.getValue() != 0) {
                        String text = String.format(" %s%d%s ", hint.getState().getText(), hint.getValue(), EMPTY.getText());
                        System.out.print(text);
                    } else {
                        System.out.print("   ");
                    }
                } else {
                    System.out.print("   ");
                }
            }
            System.out.println();
        }
        printNumberOfColumns();
    }

    private void printVerticalHint() {
        for (int row = 0; row < verticalHint.getRow(); row++) {
            for (int col = 0; col < verticalHint.getCol(); col++) {
                Tile tile = verticalHint.getTile(row, col);
                if (tile instanceof Hint) {
                    Hint hint = (Hint) tile;
                    if (hint.getValue() != 0) {
                        String text = String.format(" %s%d%s", hint.getState().getText(), hint.getValue(), TileState.EMPTY.getText());
                        System.out.print(text);
                    } else {
                        System.out.print("  ");
                    }
                } else {
                    System.out.print("  ");
                }
            }

            printRowNumber(row);
            printRowFromPlayingField(row);
            System.out.println();
        }
    }

    private void printRowNumber(int row) {
        System.out.printf(" |%2d ", row + 1);
    }

    private void printRowFromPlayingField(int row) {
        for (int column = 0; column < playingField.getCol(); column++) {
            var tile = playingField.getTile(row, column);
            switch (tile.getState()) {
                case WHITE:
                case BLACK:
                case RED:
                case BLUE:
                case YELLOW:
                case PURPLE:
                case GREEN:
                case GRAY:
                case ORANGE:
                    System.out.print(tile.getState().getText() + " # " + TileState.EMPTY.getText());
                    break;
                case MARK:
                    System.out.print(" " + tile.getState().getText() + " ");
                    break;
                default:
                    throw new RuntimeException("Unexpected tile state");
            }
        }
    }

    private void printSpace() {
        for (int column = 0; column <= verticalHint.getCol() + 1; column++) {
            System.out.print("  ");
        }
        System.out.print(" ");
    }

    private void printNumberOfColumns() {
        printSpace();
        for (int column = 0; column < playingField.getCol(); column++) {
            System.out.print("---");
        }
        System.out.println();
        printSpace();
        for (int column = 0; column < playingField.getCol(); column++) {

            System.out.print("\033[1;37m"); // set color bold
            System.out.printf("%2d ", column + 1);


        }
        System.out.println();
    }

    private void printTime() {
        long delayMillis = TimeUnit.SECONDS.toMillis(10); // 10 second delay before starting timer
        long elapsedMillis = System.currentTimeMillis() - timeAtBegin;
        if (elapsedMillis < delayMillis) {
            System.out.println("Timer will start in " + TimeUnit.MILLISECONDS.toSeconds(delayMillis - elapsedMillis) + " seconds.");
            return;
        }
        elapsedMillis -= delayMillis; // subtract delay time from elapsed time
        long remainingMillis = TimeUnit.MINUTES.toMillis(TIME_LIMIT_IN_MINUTES) - elapsedMillis;
        if (remainingMillis < 0) {
            System.out.println("Time's up!");
            playingField.setState(FieldState.FAILED);
            return;
        }
        long minutes = TimeUnit.MILLISECONDS.toMinutes(remainingMillis);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(remainingMillis) % 60;
        System.out.print("\033[31m"); // set color to red
        System.out.printf("Time remaining: %02d:%02d%n", minutes, seconds);
    }


    private void handleInput() {

        var line = scanner.nextLine().toUpperCase();
        if ("X".equals(line)) {
            System.exit(0);
        } else if ("S".equals(line)) {
            printSolved();
            System.exit(0);
        }
        var matcherColor = commandPatternForColor.matcher(line);
        var matcherMark = commandPatternForMark.matcher(line);
        if (matcherColor.matches()) {
            checkColorMatch(matcherColor);
        } else if (matcherMark.matches()) {
            int row = Integer.parseInt(matcherMark.group(3));
            int column = Integer.parseInt(matcherMark.group(5));
            if (row - 1 > playingField.getRow() || column - 1 > playingField.getCol()) {
                System.out.println("Wrong index!");
                return;
            }
            if ("M".equals(matcherMark.group(1))) {
                playingField.markTile(row - 1, column - 1);
            }
        } else {
            System.out.println("Wrong command!");
        }
    }

    private void checkColorMatch(Matcher matcherColor) {
        int row = Integer.parseInt(matcherColor.group(3)) - 1;
        int column = Integer.parseInt(matcherColor.group(5)) - 1;

        if (row < 0 || row >= playingField.getRow() || column < 0 || column >= playingField.getCol()) {
            System.out.println("Wrong index!");
            return;
        }

        String color = matcherColor.group(7);

        switch (color.toUpperCase()) {
            case "WHITE":
                playingField.colorTile(row, column, WHITE);
                break;
            case "BLACK":
                playingField.colorTile(row, column, BLACK);
                break;
            case "RED":
                playingField.colorTile(row, column, RED);
                break;
            case "BLUE":
                playingField.colorTile(row, column, BLUE);
                break;
            case "YELLOW":
                playingField.colorTile(row, column, YELLOW);
                break;
            case "PURPLE":
                playingField.colorTile(row, column, PURPLE);
                break;
            case "GREEN":
                playingField.colorTile(row, column, GREEN);
                break;
            case "GRAY":
                playingField.colorTile(row, column, GRAY);
                break;
            case "ORANGE":
                playingField.colorTile(row, column, ORANGE);
                break;
            default:
                System.out.println("Wrong color!");
                return;
        }

        if (check()) {
            playingField.setState(FieldState.SOLVED);
        }
    }

    private boolean check() {
        for (int row = 0; row < playingField.getRow(); row++) {
            for (int column = 0; column < playingField.getCol(); column++) {
                var tile = playingField.getTile(row, column);
                var solvedTile = solvedField.getTile(row, column);

                if (tile.getState() == solvedTile.getState() ||
                        (solvedTile.getState() == WHITE && tile.getState() == TileState.MARK)) {
                    continue;
                }

                return false;
            }
        }
        return true;
    }

    private void printSolved() {
        for (int row = 0; row < solvedField.getRow(); row++) {
            for (int column = 0; column < solvedField.getCol(); column++) {
                var tile = solvedField.getTile(row, column);
                System.out.print(tile.getState().getText() + "# " + TileState.EMPTY.getText());
            }

            System.out.println();
        }
        lostart();
    }

    private void lostart() {
        System.out.println("88                           ");
        System.out.println("88                         ,d");
        System.out.println("88                         88");
        System.out.println("88  ,adPPYba,  ,adPPYba, MM88MMM ");
        System.out.println("88 a8\"     \"8a I8[    \"\"   88    ");
        System.out.println("88 8b       d8  `\"Y8ba,    88   ");
        System.out.println("88 \"8a,   ,a8\" aa    ]8I   88,  ");
        System.out.println("88  `\"YbbdP\"'  `\"YbbdP\"'   \"Y888");
        System.out.println("Good luck next time!");
    }

    private void printTopScore() {
        var scores = scoreService.getTopScores("nonogram");
        for (int i = 0; i < scores.size(); i++) {
            var score = scores.get(i);
            System.out.printf("%d. %s %d\n", i + 1, score.getPlayer(), score.getPoints());
            System.out.println("-----------------");
        }
        System.out.println("--------------------------------------");
    }

    private void addRate() {
        System.out.println("Rate this game from 1 to 10");
        var scan = scanner.nextLine();
        int rating = Integer.parseInt(scan);
        if (rating >= 0 && rating <= 10) {
            ratingService.setRating(new Rating(username, "nonogram", rating, new Date()));
        } else {
            System.out.println("You entered an invalid rating!!");
            addRate();
        }
    }

    private void printRatings() {
        var ratings = ratingService.getLastFiveRatings("nonogram");
        for (int i = 0; i < ratings.size(); i++) {
            var rate = ratings.get(i);
            System.out.printf("%d. %s %d out of 10\n \n", i + 1, rate.getPlayer(), rate.getRating());
            System.out.println("-----------------");
        }
        System.out.println("--------------------------------------");
    }

    private void addComment() {
        System.out.println("Leave a comment please!\n");
        String scan = scanner.nextLine();
        commentService.addComment(new Comment(username, scan, "nonogram", new Date()));
    }

    private void printComments() {
        var comments = commentService.getComments("nonogram");
        for (int i = 0; i < comments.size(); i++) {
            var c = comments.get(i);
            System.out.printf("%s : %s \n\n", c.getComment(), c.getPlayer());
        }
    }
    private void printWin(){
        System.out.println("██╗   ██╗ ██████╗ ██╗   ██╗    ██╗    ██╗ ██████╗ ███╗   ██╗██╗");
        System.out.println("╚██╗ ██╔╝██╔═══██╗██║   ██║    ██║    ██║██╔═══██╗████╗  ██║██║");
        System.out.println(" ╚████╔╝ ██║   ██║██║   ██║    ██║ █╗ ██║██║   ██║██╔██╗ ██║██║");
        System.out.println("  ╚██╔╝  ██║   ██║██║   ██║    ██║███╗██║██║   ██║██║╚██╗██║╚═╝");
        System.out.println("   ██║   ╚██████╔╝╚██████╔╝    ╚███╔███╔╝╚██████╔╝██║ ╚████║██");
        System.out.println("   ╚═╝    ╚═════╝  ╚═════╝      ╚══╝╚══╝  ╚═════╝ ╚═╝  ╚═══╝╚═╝");
    }

}
