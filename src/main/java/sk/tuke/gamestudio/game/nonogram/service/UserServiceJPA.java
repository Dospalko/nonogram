package sk.tuke.gamestudio.game.nonogram.service;

import sk.tuke.gamestudio.game.nonogram.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class UserServiceJPA implements UserService {
    @PersistenceContext
    private EntityManager entityManager;

    private User user;

    @Override
    public void addUser(User user){
        entityManager.persist(user);
    }

    public User getUser(String login, String password) {
        List<User> list = entityManager.createQuery("select u from User u where u.login = :login and u.password = :password", User.class)
                .setParameter("login", login)
                .setParameter("password", password)
                .setMaxResults(1)
                .getResultList();
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public void reset() {
        entityManager.createQuery("DELETE FROM User").executeUpdate();
    }

}
