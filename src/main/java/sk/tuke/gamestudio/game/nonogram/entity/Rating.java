package sk.tuke.gamestudio.game.nonogram.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class Rating implements Serializable {

    @Id
    @GeneratedValue
    private int id;
    private String player;

    private String game;

    private int rating;

    private Date ratedAt;

    public Rating(String player, String game, int rating, Date ratedAt) {
        this.player = player;
        this.game = game;
        this.rating = rating;
        this.ratedAt = ratedAt;
    }

    public Rating() {

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

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Date getRatedAt() {
        return ratedAt;
    }

    public void setRatedAt(Date ratedAt) {
        this.ratedAt = ratedAt;
    }

    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("E MMM dd HH:mm:ss zzz yyyy");
        return "Rating{" +
                "player='" + player + '\'' +
                ", game='" + game + '\'' +
                ", rating=" + rating +
                ", ratedAt=" + dateFormat.format(ratedAt) +
                '}';
    }
}








