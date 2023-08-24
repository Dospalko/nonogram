package sk.tuke.gamestudio.game.nonogram.entity;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class Comment {

    @Id
    @GeneratedValue
    private int id;
    private String player;
    private String comment;
    private Date commentedAt;
    private String game;
    public Comment(String player, String comment, String game, Date commentedAt) {
        this.player = player;
        this.game = game;
        this.comment = comment;
        this.commentedAt = commentedAt;
    }

    public Comment() {

    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }


    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getGame(){
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public Date getCommentedAt() {
        return commentedAt;
    }

    public void setCommentedAt(Date commentedAt) {
        this.commentedAt = commentedAt;
    }

    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("E MMM dd HH:mm:ss zzz yyyy");
        return "Comment{" +
                "player='" + player + '\'' +
                ", comment='" + comment + '\'' +
                ", commentedAt=" + dateFormat.format(commentedAt) +
                '}';
    }
}