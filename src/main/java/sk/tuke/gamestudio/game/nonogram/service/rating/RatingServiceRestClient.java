package sk.tuke.gamestudio.game.nonogram.service.rating;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import sk.tuke.gamestudio.game.nonogram.entity.Rating;

import java.util.Arrays;
import java.util.List;

public class RatingServiceRestClient implements RatingService{

    @Autowired
    private RestTemplate restTemplate;

    private String url = "http://localhost:8080/api/rating";
    @Override
    public void setRating(Rating rating) {
        restTemplate.postForEntity(url, rating, Rating.class);
    }
    @Override
    public double getAverageRating(String game) {
        return restTemplate.getForObject(url + "/average/" + game, Double.class);
    }

    @Override
    public List<Rating> getLastFiveRatings(String game) {
        return Arrays.asList(restTemplate.getForEntity(url + "/" + game, Rating[].class).getBody());
    }



    @Override
    public void reset() {
        throw new UnsupportedOperationException("Reset is not supported!");
    }
}
