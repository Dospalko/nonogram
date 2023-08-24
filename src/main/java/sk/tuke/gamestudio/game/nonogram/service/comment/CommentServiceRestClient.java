package sk.tuke.gamestudio.game.nonogram.service.comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import sk.tuke.gamestudio.game.nonogram.entity.Comment;

import java.util.Arrays;
import java.util.List;

public class CommentServiceRestClient implements CommentService{

    @Autowired
    private RestTemplate restTemplate;

    private String url = "http://localhost:8080/api/comment";
    @Override
    public void addComment(Comment comment) {
        restTemplate.postForEntity(url,comment, Comment.class);
    }

    @Override
    public List<Comment> getComments(String game) {
        return Arrays.asList(restTemplate.getForEntity(url + "/" + game, Comment[].class).getBody());
    }

    @Override
    public void reset() {
        throw new UnsupportedOperationException("Reset is not supported!");
    }
}
