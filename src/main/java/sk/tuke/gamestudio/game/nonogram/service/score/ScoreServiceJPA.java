package sk.tuke.gamestudio.game.nonogram.service.score;

import sk.tuke.gamestudio.game.nonogram.entity.Score;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class ScoreServiceJPA implements ScoreService{
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public void addScore(Score score) {
        entityManager.persist(score);
    }

    @Override
    public List<Score> getTopScores(String game) {
       return (List<Score>) entityManager.createQuery("select s from Score s where s.game= :game order by s.points desc")
               .setParameter("game", game)
               .setMaxResults(10)
               .getResultList();
    }

    @Override
    public void reset() {
        entityManager.createNativeQuery("DELETE from SCORE").executeUpdate();
    }
}
