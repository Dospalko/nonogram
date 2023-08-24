package sk.tuke.gamestudio.game.nonogram.service.comment;

import sk.tuke.gamestudio.game.nonogram.entity.Comment;

import java.util.List;

public interface CommentService {
    void addComment(Comment comment);

    List<Comment> getComments(String game);


    void reset();
}
