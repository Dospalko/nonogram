package sk.tuke.gamestudio.game.nonogram.server.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;
import sk.tuke.gamestudio.game.nonogram.core.*;
import sk.tuke.gamestudio.game.nonogram.entity.Comment;
import sk.tuke.gamestudio.game.nonogram.entity.Rating;
import sk.tuke.gamestudio.game.nonogram.entity.Score;
import sk.tuke.gamestudio.game.nonogram.entity.User;
import sk.tuke.gamestudio.game.nonogram.service.comment.CommentService;
import sk.tuke.gamestudio.game.nonogram.service.rating.RatingService;
import sk.tuke.gamestudio.game.nonogram.service.score.ScoreService;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static sk.tuke.gamestudio.game.nonogram.core.TileState.WHITE;

@Controller
@RequestMapping("/nonogram")
@Scope(WebApplicationContext.SCOPE_SESSION)
public class NonogramController {

    private int fieldNumber = new RandomNumberFieldGeneration().generation();
    private List<TileState> colors;
    private final int[][] map = {
            {8, 8, 4, 4},
            {10, 6, 7, 6},
            {5, 7, 7, 5},
            {4, 4, 2, 2},
            {16, 12, 11, 10},
    };
    private boolean marking;
    private TileState color = TileState.BLACK;
    private int hintsLeft = 3;
    private int[] fieldData = map[fieldNumber - 1];
    private PlayingField playingField = new PlayingField(fieldData[0], fieldData[1]);
    private SolvedField solvedField = new SolvedField(fieldData[0], fieldData[1], fieldNumber);
    private HintField verticalHint = new HintField(fieldData[0], fieldData[2], fieldNumber, true);
    private HintField horizontalHint = new HintField(fieldData[1], fieldData[3], fieldNumber, false);
    @Autowired
    private CommentService commentService;
    @Autowired
    private UserController userController;

    @Autowired
    private RatingService ratingService;
    @Autowired
    private ScoreService scoreService;
    private int step = 0;
    private long timeAtBegin;
    private static final int TIME_LIMIT_IN_MINUTES = 5; // 5 minutes time limit



    public NonogramController() throws FileNotFoundException {
    }


    @RequestMapping
    public String nonogram(@RequestParam(required = false) Integer row, @RequestParam(required = false) Integer column,
                           Model model) {

        timeAtBegin = System.currentTimeMillis();
        if (row != null && column != null) {
            if (marking) {
                playingField.markTile(row, column);
            } else {
                playingField.colorTile(row, column, color);
                step++;
            }

        }
        getComments(model);
        getAvgRating(model);
        getRating(model);
        getScore(model);
        boolean completed = check();
        if (completed) {

            long timeAtEnd = System.currentTimeMillis();
            long timeSpentInMillis = timeAtEnd - timeAtBegin;
            long timeSpentInSeconds = TimeUnit.MILLISECONDS.toSeconds(timeSpentInMillis);
            int score = 500 - step;
            model.addAttribute("message", "Congratulations, you have completed the game!");
            model.addAttribute("score", score);
            model.addAttribute("step",step);
            model.addAttribute("time",timeSpentInSeconds);
            scoreService.addScore(new Score(userController.getLoggedUser().getLogin(),"nonogram",score,new Date()));
            return "completed";
        }

        return "nonogram";
    }


    @RequestMapping("/mark")
    public String changeMarking() {
        marking = !marking;
        return "redirect:/nonogram";
    }

    public boolean isMarking() {
        return marking;
    }


    public String getCurrentTime() {
        return new Date().toString();
    }

    private boolean check() {
        for (int row = 0; row < playingField.getRow(); row++) {
            for (int column = 0; column < playingField.getCol(); column++) {
                if ((playingField.getTile(row, column).getState() == solvedField.getTile(row, column).getState()) ||
                        solvedField.getTile(row, column).getState() == WHITE &&
                                playingField.getTile(row, column).getState() == TileState.MARK) {
                    continue;
                }
                return false;
            }
        }

        return true;
    }

    @PostMapping("/newgame")
    public String newGame(Model model) throws FileNotFoundException {
        fieldNumber = new RandomNumberFieldGeneration().generation();;
        colors = null;
        color = TileState.BLACK;
        fieldData = map[fieldNumber - 1];
        playingField = new PlayingField(fieldData[0], fieldData[1]);
        solvedField = new SolvedField(fieldData[0], fieldData[1], fieldNumber);
        verticalHint = new HintField(fieldData[0], fieldData[2], fieldNumber, true);
        horizontalHint = new HintField(fieldData[1], fieldData[3], fieldNumber, false);
        return "redirect:/nonogram";
    }

    @RequestMapping("/hint")
    public String getHint(Model model) {
        if (hintsLeft <= 0) {
            return "redirect:/nonogram";
        }
        int row, column;
        TileState ts;
        do {
            row = new Random().nextInt(solvedField.getRow());
            column = new Random().nextInt(solvedField.getCol());
            ts = solvedField.getTile(row, column).getState();
        } while (ts == WHITE || playingField.getTile(row, column).getState() == ts);
        playingField.colorTile(row, column, ts);
        hintsLeft--;
        getComments(model);
        return "redirect:/nonogram";
    }

    public int gethintsLeft(){
        return hintsLeft;
    }
    public String getHtmlField1() {
        StringBuilder sbField = new StringBuilder();
        for (int col = 0; col < horizontalHint.getCol(); col++) {
            sbField.append("<tr>\n");
            printEmptySpace(sbField, verticalHint.getCol());
            for (int row = horizontalHint.getRow() - 1; row >= 0; row--) {
                Tile tile = horizontalHint.getTile(row, col);
                sbField.append("<td>\n");
                if (tile instanceof Hint) {
                    Hint hint = (Hint) tile;
                    if (hint.getValue() != 0) {
                        sbField.append(getHintImage(hint));
                    }
                }
                sbField.append("</td>\n");
            }
            sbField.append("</tr>\n");
        }
        hintFields(sbField);
        return sbField.toString();
    }


    private void hintFields(StringBuilder sbField) {
        for (int row = 0; row < verticalHint.getRow(); row++) {
            sbField.append("<tr>\n");
            for (int col = 0; col < verticalHint.getCol(); col++) {
                Tile tile = verticalHint.getTile(row, col);
                sbField.append("<td>\n");
                if (tile instanceof Hint) {
                    Hint hint = (Hint) tile;
                    if (hint.getValue() != 0) {
                        sbField.append(getHintImage(hint));
                    }
                }
                sbField.append("</td>\n");
            }
            printRowFromPlayingField(sbField, row);
            sbField.append("</tr>\n");
        }
    }

    private void printRowFromPlayingField(StringBuilder sbField, int row) {
        for (int column = 0; column < playingField.getCol(); column++) {
            Tile tile = playingField.getTile(row, column);
            sbField.append("<td>\n");
            sbField.append("<a href='?row=" + row + "&column=" + column + "'>\n");
            sbField.append(getTileImage(tile));
            sbField.append("</a>\n");
            sbField.append("</td>\n");
        }
    }

    private void printEmptySpace(StringBuilder sbField, int colCount) {
        for (int column = 0; column < colCount; column++) {
            sbField.append("<td>\n");
            sbField.append(" ");
            sbField.append("</td>\n");
        }
    }

    private String getHintImage(Hint hint) {
        String color = hint.getState().name().toLowerCase();
        int value = hint.getValue();
        return "<img src='/images/" + color + value + ".png'>";
    }

    private String getTileImage(Tile tile) {
        String color = tile.getState().name().toLowerCase();
        return "<img src='/images/" + color + ".png'>";
    }
    @RequestMapping("/color")
    public String setColor(@RequestParam(required = false) TileState ts, Model model) {
        color = ts;
        return "redirect:/nonogram";
    }

    public String printColors() {
        loadColors();
        return colors.stream().map(this::createColorLink).collect(Collectors.joining());
    }

    private String createColorLink(TileState ts) {
        String link = "<a href='/nonogram/color?ts=" + ts + "'";
        if (ts == color) {
            link += " class='active'";
        }
        link += ">\n<img src='/images/" + ts.name().toLowerCase() + ".png'>\n</a>\n";
        return link;
    }

    public String getColor() {
        String imageSrc = "/images/" + color.name().toLowerCase() + ".png";
        return "<img src='" + imageSrc + "'>";
    }

    private void loadColors() {
        colors = Arrays.asList(TileState.values());
    }

//Services


    private void getComments(Model model){
        model.addAttribute("comments", commentService.getComments("nonogram"));
    }
    @RequestMapping("/comment")
    public String setComment(String comment){
        if(comment != null){
            commentService.addComment(new Comment(userController.getLoggedUser().getLogin(), comment,"nonogram", new Date()));
        }

        return "redirect:/nonogram";
    }
    @RequestMapping("/rating")
    public String setRating(int rating, Model model) {
        User user = userController.getLoggedUser();
        if (user != null && rating >= 0 && rating <= 5) {
            ratingService.setRating(new Rating(user.getLogin(), "nonogram", rating, new Date()));
        } else {
            model.addAttribute("error", "Invalid rating input. Please enter a number between 0 and 5.");
        }
        return "redirect:/nonogram";
    }

    private void getRating(Model model) {
        model.addAttribute("ratings",ratingService.getLastFiveRatings("nonogram"));
    }
    private void getAvgRating(Model model){
        model.addAttribute("average_rating",ratingService.getAverageRating("nonogram"));
    }

    private void getScore(Model model){
        model.addAttribute("scores",scoreService.getTopScores("nonogram"));
    }

}
