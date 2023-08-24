package sk.tuke.gamestudio.game.nonogram;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.client.RestTemplate;
import sk.tuke.gamestudio.game.nonogram.consoleui.ConsoleUI;
import sk.tuke.gamestudio.game.nonogram.service.comment.CommentService;
import sk.tuke.gamestudio.game.nonogram.service.comment.CommentServiceRestClient;
import sk.tuke.gamestudio.game.nonogram.service.rating.RatingService;
import sk.tuke.gamestudio.game.nonogram.service.rating.RatingServiceRestClient;
import sk.tuke.gamestudio.game.nonogram.service.score.ScoreService;
import sk.tuke.gamestudio.game.nonogram.service.score.ScoreServiceRestClient;

import java.io.FileNotFoundException;

@SpringBootApplication
@Configuration
@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX, pattern = "sk.tuke.gamestudio.game.nonogram.server.*"))
public class SpringClient {
    public static void main(String[] args) {
        //SpringApplication.run(SpringClient.class);
        new SpringApplicationBuilder(SpringClient.class).web(WebApplicationType.NONE).run(args);
    }


    @Bean
    public CommandLineRunner runner(ConsoleUI consoleUI){
        return s -> consoleUI.play();


    }

    @Bean
    public ConsoleUI consoleUI() throws FileNotFoundException {
        return new ConsoleUI(4);
    }

    @Bean
    public ScoreService scoreService() {
        return new ScoreServiceRestClient();
    }


    @Bean
    public RatingService ratingService() {
        return new RatingServiceRestClient();
    }
    @Bean
    public CommentService commentService() {
        return new CommentServiceRestClient();
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

}
