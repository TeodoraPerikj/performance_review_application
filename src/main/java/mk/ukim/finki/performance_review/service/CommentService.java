package mk.ukim.finki.performance_review.service;

import mk.ukim.finki.performance_review.model.Comment;

import java.util.List;

public interface CommentService {

    Comment create(String username, Long taskId, String comment);

    Comment edit(Long id, String comment);

    Comment delete(Long id);

    Comment findById(Long id);

    List<Comment> listCommentsForATask(Long taskId);

    List<Comment> listCommentsByDateForATask(String date, Long taskId);

    List<Comment> listCommentsByTaskAndUser(Long taskId, String username);

    List<Comment> listCommentsByDateForTaskAndUser(Long taskId, String username, String date);
}
