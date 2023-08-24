package sk.tuke.gamestudio.game.nonogram.service.comment;

import sk.tuke.gamestudio.game.nonogram.entity.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
@Transactional
public class CommentServiceJPA implements CommentService {

    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public void addComment(Comment comment) {
        entityManager.persist(comment);
    }

    @Override
    public List<Comment> getComments(String game) {
        return entityManager.createQuery("SELECT c FROM Comment c WHERE c.game = :game ORDER BY c.commentedAt DESC", Comment.class)
                .setParameter("game", game)
                .getResultList();
    }

    @Override
    public void reset() {
        entityManager.createQuery("DELETE FROM Comment").executeUpdate();
    }
}
