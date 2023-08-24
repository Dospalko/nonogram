package sk.tuke.gamestudio.game.nonogram.service.rating;

import sk.tuke.gamestudio.game.nonogram.entity.Rating;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class RatingServiceJPA implements RatingService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void setRating(Rating rating) {
        TypedQuery<Rating> query = entityManager.createQuery(
                "SELECT r FROM Rating r WHERE r.player = :player AND r.game = :game",
                Rating.class);
        query.setParameter("player", rating.getPlayer());
        query.setParameter("game", rating.getGame());

        List<Rating> ratings = query.getResultList();

        if (!ratings.isEmpty()) {
            Rating existingRating = ratings.get(0);
            existingRating.setRating(rating.getRating());
            existingRating.setRatedAt(rating.getRatedAt());
            entityManager.merge(existingRating);
        } else {
            entityManager.persist(rating);
        }
    }


    @Override
    public double getAverageRating(String game) {
        TypedQuery<Double> query = entityManager.createQuery("SELECT AVG(r.rating) FROM Rating r WHERE r.game = :game", Double.class);
        query.setParameter("game", game);
        return query.getSingleResult();
    }

    @Override
    public List<Rating> getLastFiveRatings(String game) {
        TypedQuery<Rating> query = entityManager.createQuery("SELECT r FROM Rating r WHERE r.game = :game ORDER BY r.id DESC", Rating.class);
        query.setParameter("game", game);
        query.setMaxResults(5);
        return query.getResultList();
    }

    @Override
    public void reset() {
        entityManager.createQuery("DELETE FROM Rating").executeUpdate();
    }
}
