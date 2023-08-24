package sk.tuke.gamestudio.game.nonogram.service.rating;

import sk.tuke.gamestudio.game.nonogram.entity.Rating;

import java.util.List;

public interface RatingService {

    void setRating(Rating rating);


    double getAverageRating(String game);

    List<Rating> getLastFiveRatings(String game);

    void reset();
}
