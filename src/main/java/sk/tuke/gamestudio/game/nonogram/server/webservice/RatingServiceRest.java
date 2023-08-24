package sk.tuke.gamestudio.game.nonogram.server.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sk.tuke.gamestudio.game.nonogram.entity.Rating;
import sk.tuke.gamestudio.game.nonogram.service.rating.RatingService;

import java.util.List;

@RestController
@RequestMapping("/api/rating")
public class RatingServiceRest {

    @Autowired
    private RatingService ratingService;

    @PostMapping
    public void setRating(@RequestBody Rating rating){
        ratingService.setRating(rating);
    }

    @GetMapping("/{game}")
    public List<Rating> getLastFiveRatings(@PathVariable String game) {
        return ratingService.getLastFiveRatings(game);
    }

    @GetMapping("/{game}/average")
    public double getAverageRating(@PathVariable  String game){
        return ratingService.getAverageRating(game);
    }

}
