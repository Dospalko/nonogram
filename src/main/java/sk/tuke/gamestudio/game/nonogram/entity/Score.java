package sk.tuke.gamestudio.game.nonogram.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Score implements Serializable {

    @Id
    @GeneratedValue
    private int ident;
    private String player;

    private String game;

    private int points;

    private Date playedAt;

    public Score(String player, String game, int points, Date playedAt) {
        this.player = player;
        this.game = game;
        this.points = points;
        this.playedAt = playedAt;
    }

    public Score() {

    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Date getPlayedAt() {
        return playedAt;
    }

    public void setPlayedAt(Date playedAt) {
        this.playedAt = playedAt;
    }

    @Override
    public String toString() {
        return "Score{" +
                "player='" + player + '\'' +
                ", game='" + game + '\'' +
                ", points=" + points +
                ", playedAt=" + playedAt +
                '}';
    }
}
