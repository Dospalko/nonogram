package sk.tuke.gamestudio.game.nonogram.service.rating;

import sk.tuke.gamestudio.game.nonogram.entity.Rating;
import sk.tuke.gamestudio.game.nonogram.service.GameStudioException;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class RatingServiceJDBC implements RatingService {
    private static final String JDBC_URL = "jdbc:postgresql://localhost/gamestudio";
    private static final String JDBC_USER = "postgres";
    private static final String JDBC_PASSWORD = "heslo";
    private static final String CREATE_STATEMENT = "CREATE TABLE rating (player VARCHAR(255) NOT NULL, game VARCHAR(255) NOT NULL, rating INTEGER NOT NULL, rated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL)";
    private static final String INSERT_STATEMENT = "INSERT INTO rating (player, game, rating, rated_at) VALUES (?, ?, ?, ?)";
    private static final String SELECT_STATEMENT = "SELECT AVG(rating) FROM rating WHERE game = ?";
    private static final String DELETE_STATEMENT = "DELETE FROM rating";
    private static final String LAST_FIVE_SELECT_STATEMENT = "SELECT player, game, rating, rated_at FROM rating WHERE game = ? ORDER BY rated_at DESC LIMIT 5";

    @Override
    public void setRating(Rating rating) {
        try (var connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             var statement = connection.prepareStatement(INSERT_STATEMENT);
        ) {
            statement.setString(1, rating.getPlayer());
            statement.setString(2, rating.getGame());
            statement.setInt(3, rating.getRating());
            statement.setTimestamp(4, new Timestamp(rating.getRatedAt().getTime()));
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new GameStudioException(e);
        }
    }

    @Override
    public double getAverageRating(String game) {
        try (var connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             var statement = connection.prepareStatement(SELECT_STATEMENT)
        ) {
            statement.setString(1, game);
            try (var rs = statement.executeQuery()) {
                if (rs.next())
                    return rs.getDouble(1);
                else
                    return 0.0;
            }
        } catch (SQLException e) {
            throw new GameStudioException(e);
        }
    }
    @Override
    public List<Rating> getLastFiveRatings(String game) {
        try (var connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             var statement = connection.prepareStatement(LAST_FIVE_SELECT_STATEMENT)
        ) {
            statement.setString(1, game);
            try (var rs = statement.executeQuery()) {
                var ratings = new ArrayList<Rating>();
                while (rs.next())
                    ratings.add(new Rating(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getTimestamp(4)));
                return ratings;
            }
        } catch (SQLException e) {
            throw new GameStudioException(e);
        }
    }

    @Override
    public void reset() {
        try (var connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             var statement = connection.createStatement();
        ) {
            statement.executeUpdate(DELETE_STATEMENT);
        } catch (SQLException e) {
            throw new GameStudioException(e);
        }
    }
}
