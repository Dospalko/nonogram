
package sk.tuke.gamestudio.game.nonogram.service.comment;

import sk.tuke.gamestudio.game.nonogram.entity.Comment;
import sk.tuke.gamestudio.game.nonogram.service.GameStudioException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentServiceJDBC implements CommentService {
    private static final String JDBC_URL = "jdbc:postgresql://localhost/gamestudio";
    private static final String JDBC_USER = "postgres";
    private static final String JDBC_PASSWORD = "heslo";
    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS comments ("
            + "player VARCHAR(255) NOT NULL,"
            + "game VARCHAR(255) NOT NULL,"
            + "comment_text TEXT NOT NULL,"
            + "commented_at TIMESTAMP WITHOUT TIME ZONE NOT NULL"
            + ")";
    private static final String INSERT_COMMENT = "INSERT INTO comments (player, game, comment_text, commented_at) VALUES (?, ?, ?, ?)";
    private static final String SELECT_COMMENTS = "SELECT player, game, comment_text, commented_at FROM comments WHERE game = ?";
    private static final String DELETE_COMMENTS = "DELETE FROM comments";

    @Override
    public void addComment(Comment comment) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(INSERT_COMMENT)) {
            statement.setString(1, comment.getPlayer());
            statement.setString(2, comment.getGame());
            statement.setString(3, comment.getComment());
            statement.setTimestamp(4, new Timestamp(comment.getCommentedAt().getTime()));
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new GameStudioException(e);
        }
    }

    @Override
    public List<Comment> getComments(String game) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(SELECT_COMMENTS)) {
            statement.setString(1, game);
            try (ResultSet rs = statement.executeQuery()) {
                List<Comment> comments = new ArrayList<>();
                while (rs.next()) {
                    Comment comment = new Comment(
                            rs.getString("player"),
                            rs.getString("comment_text"),
                            rs.getString("game"),
                            rs.getTimestamp("commented_at")
                    );
                    comments.add(comment);
                }
                return comments;
            }
        } catch (SQLException e) {
            throw new GameStudioException(e);
        }
    }

    @Override
    public void reset() {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(DELETE_COMMENTS);
        } catch (SQLException e) {
            throw new GameStudioException(e);
        }
    }

    static {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(CREATE_TABLE);
        } catch (SQLException e) {
            throw new GameStudioException(e);
        }
    }
}