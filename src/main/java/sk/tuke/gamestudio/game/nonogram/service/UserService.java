package sk.tuke.gamestudio.game.nonogram.service;

import org.springframework.stereotype.Service;
import sk.tuke.gamestudio.game.nonogram.entity.User;

@Service
public interface UserService {
    void addUser(User user);

    User getUser(String login, String password);

    void reset();
}

