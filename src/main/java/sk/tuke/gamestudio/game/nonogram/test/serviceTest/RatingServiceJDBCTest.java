package sk.tuke.gamestudio.game.nonogram.test.serviceTest;

import org.junit.jupiter.api.*;
import sk.tuke.gamestudio.game.nonogram.entity.Rating;
import sk.tuke.gamestudio.game.nonogram.service.rating.RatingService;
import sk.tuke.gamestudio.game.nonogram.service.rating.RatingServiceJDBC;

import java.time.Instant;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RatingServiceJDBCTest {
    private static final String GAME_NAME = "Nonogram";
    private static final Rating RATING_1 = new Rating("player1", GAME_NAME, 5,Date.from(Instant.now()));
    private static final Rating RATING_2 = new Rating("player2", GAME_NAME, 3,Date.from(Instant.now()));
    private static final Rating RATING_3 = new Rating("player3", GAME_NAME, 4,Date.from(Instant.now()));
    private static RatingService ratingService;

    @BeforeAll
    static void setUp() {
        ratingService = new RatingServiceJDBC();
    }

    @AfterEach
    void cleanUp() {
        ratingService.reset();
    }

    @Test
    @Order(1)
    void setRating() {
        ratingService.setRating(RATING_1);
        ratingService.setRating(RATING_2);
        ratingService.setRating(RATING_3);
        var ratings = ratingService.getLastFiveRatings(GAME_NAME);
        assertEquals(3, ratings.size());
    }

    @Test
    @Order(2)
    void getAverageRating() {
        ratingService.setRating(RATING_1);
        ratingService.setRating(RATING_2);
        ratingService.setRating(RATING_3);
        var avgRating = ratingService.getAverageRating(GAME_NAME);
        assertEquals(4.0, avgRating);
    }


}
