package sk.tuke.gamestudio.game.nonogram.test.serviceTest;

import org.junit.jupiter.api.*;
import sk.tuke.gamestudio.game.nonogram.entity.Comment;
import sk.tuke.gamestudio.game.nonogram.service.comment.CommentService;
import sk.tuke.gamestudio.game.nonogram.service.comment.CommentServiceJDBC;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CommentServiceJDBCTest {

    private static CommentService commentService;

    @BeforeAll
    static void init() {
        commentService = new CommentServiceJDBC();
    }

    @AfterEach
    void clearComments() {
        commentService.reset();
    }

    @Test
    @Order(1)
    void addComment() {
        Comment comment = new Comment("player1",  "This is a test comment.", "nonogram",new Date());
        commentService.addComment(comment);

        var comments = commentService.getComments("nonogram");

        assertEquals(1, comments.size());
        assertEquals(comment, comments.get(0));
    }

    @Test
    @Order(2)
    void getComments() {
        Comment comment1 = new Comment("player1",  "This is a test comment.","nonogram", new Date());
        Comment comment2 = new Comment("player2",  "This is another test comment.","nonogram", new Date());
        Comment comment3 = new Comment("player3",  "This is a test comment for another game.","sudoku", new Date());

        commentService.addComment(comment1);
        commentService.addComment(comment2);
        commentService.addComment(comment3);

        List<Comment> comments = commentService.getComments("nonogram");

        assertEquals(2, comments.size());
    }

    @Test
    @Order(3)
    void reset() {
        Comment comment = new Comment("player1", "nonogram", "This is a test comment.", new Date());
        commentService.addComment(comment);

        commentService.reset();

        List<Comment> comments = commentService.getComments("nonogram");

        assertTrue(comments.isEmpty());
    }

}
