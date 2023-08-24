package sk.tuke.gamestudio.game.nonogram;

import sk.tuke.gamestudio.game.nonogram.entity.Comment;
import sk.tuke.gamestudio.game.nonogram.entity.Rating;
import sk.tuke.gamestudio.game.nonogram.entity.Score;
import sk.tuke.gamestudio.game.nonogram.service.comment.CommentService;
import sk.tuke.gamestudio.game.nonogram.service.comment.CommentServiceJDBC;
import sk.tuke.gamestudio.game.nonogram.service.rating.RatingService;
import sk.tuke.gamestudio.game.nonogram.service.rating.RatingServiceJDBC;
import sk.tuke.gamestudio.game.nonogram.service.score.ScoreService;
import sk.tuke.gamestudio.game.nonogram.service.score.ScoreServiceJDBC;

import java.util.Date;

public class JBDCTest {
    public static void main(String[] args) throws Exception {
        ScoreService scoreService =  new ScoreServiceJDBC();

        scoreService.reset();
        scoreService.addScore(new Score("Dominik","nonogram",110,new Date()));
        scoreService.addScore(new Score("Dominik1","nonogram",150,new Date()));
        scoreService.addScore(new Score("Dominik2","nonogram",190,new Date()));
        scoreService.addScore(new Score("Dominik3","nonogram",200,new Date()));

        var scores = scoreService.getTopScores("nonogram");
        System.out.println(scores);

        RatingService ratingService = new RatingServiceJDBC();

        ratingService.setRating(new Rating("Dominik","nonogram",10, new Date()));

        ratingService.setRating(new Rating("Dominik","nonogram",20, new Date()));

        ratingService.setRating(new Rating("Dominik","nonogram",30, new Date()));

        ratingService.setRating(new Rating("Dominik","nonogram",40, new Date()));
        var rating = ratingService.getAverageRating("nonogram");
        var pes = ratingService.getLastFiveRatings("nonogram");
        System.out.println(rating);
        System.out.println(pes);


        CommentService commentService = new CommentServiceJDBC();

       commentService.addComment(new Comment("DOMINIK","moj koemnt","nonogram",new Date()));

       var com = commentService.getComments("nonogram");

        System.out.println(com);


    }
}
