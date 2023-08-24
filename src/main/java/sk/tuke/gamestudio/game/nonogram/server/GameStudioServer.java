package sk.tuke.gamestudio.game.nonogram.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sk.tuke.gamestudio.game.nonogram.service.UserService;
import sk.tuke.gamestudio.game.nonogram.service.UserServiceJPA;
import sk.tuke.gamestudio.game.nonogram.service.comment.CommentService;
import sk.tuke.gamestudio.game.nonogram.service.comment.CommentServiceJPA;
import sk.tuke.gamestudio.game.nonogram.service.rating.RatingService;
import sk.tuke.gamestudio.game.nonogram.service.rating.RatingServiceJPA;
import sk.tuke.gamestudio.game.nonogram.service.score.ScoreService;
import sk.tuke.gamestudio.game.nonogram.service.score.ScoreServiceJPA;

@SpringBootApplication
@Configuration
@EntityScan(basePackages = "sk.tuke.gamestudio.game.nonogram.entity")
public class GameStudioServer {
    public static void main(String[] args) {
        SpringApplication.run(GameStudioServer.class);
    }


    @Bean
    public ScoreService scoreService(){
        return new ScoreServiceJPA();
    }

    @Bean
    public CommentService commentService(){
        return new CommentServiceJPA();
    }
    @Bean
    public RatingService ratingService(){
        return new RatingServiceJPA();
    }
    @Bean
    public UserService userService(){
        return new UserServiceJPA();
    }
}
