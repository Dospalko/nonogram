package sk.tuke.gamestudio.game.nonogram.service.score;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import sk.tuke.gamestudio.game.nonogram.entity.Score;

import java.util.Arrays;
import java.util.List;

public class ScoreServiceRestClient implements ScoreService{

    private String url = "http://localhost:8080/api/score";

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void addScore(Score score) {
        restTemplate.postForEntity(url,score,Score.class);
    }

    @Override
    public List<Score> getTopScores(String game) {
        return Arrays.asList(restTemplate.getForEntity(url + "/" + game, Score[].class).getBody());
    }


    @Override
    public void reset() {
        throw new UnsupportedOperationException("Reset is not supported!");
    }
}
