package sk.tuke.gamestudio.game.nonogram.service.score;

import sk.tuke.gamestudio.game.nonogram.entity.Score;

import java.util.List;

public interface ScoreService {

    void addScore(Score score);


    List<Score> getTopScores(String game);


    void reset();

}

