package sk.tuke.gamestudio.game.nonogram.test.serviceTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import sk.tuke.gamestudio.game.nonogram.entity.Score;
import sk.tuke.gamestudio.game.nonogram.service.score.ScoreService;
import sk.tuke.gamestudio.game.nonogram.service.score.ScoreServiceJDBC;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ScoreServiceJDBCTest {

    private ScoreService scoreService;

    @Before
    public void setUp() throws Exception {
        scoreService = new ScoreServiceJDBC();
    }

    @After
    public void tearDown() throws Exception {
        scoreService.reset();
    }

    @Test
    public void testAddScore() {
        Score score = new Score("John", "nonogram", 100, new Date());
        scoreService.addScore(score);
        List<Score> scores = scoreService.getTopScores("nonogram");
        assertEquals(1, scores.size());
        assertEquals(score.getPlayer(), scores.get(0).getPlayer());
        assertEquals(score.getGame(), scores.get(0).getGame());
        assertEquals(score.getPoints(), scores.get(0).getPoints());
    }

    @Test
    public void testGetTopScores() {
        Score score1 = new Score("John", "nonogram", 100, new Date());
        Score score2 = new Score("Mary", "nonogram", 90, new Date());
        Score score3 = new Score("Tom", "nonogram", 80, new Date());
        scoreService.addScore(score1);
        scoreService.addScore(score2);
        scoreService.addScore(score3);
        List<Score> scores = scoreService.getTopScores("nonogram");
        assertEquals(3, scores.size());
        assertEquals(score1.getPlayer(), scores.get(0).getPlayer());
        assertEquals(score2.getPlayer(), scores.get(1).getPlayer());
        assertEquals(score3.getPlayer(), scores.get(2).getPlayer());
    }

    @Test
    public void testReset() {
        Score score = new Score("John", "nonogram", 100, new Date());
        scoreService.addScore(score);
        scoreService.reset();
        List<Score> scores = scoreService.getTopScores("nonogram");
        assertEquals(0, scores.size());
    }
}
