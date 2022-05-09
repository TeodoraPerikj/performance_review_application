package mk.ukim.finki.performance_review.service;

import mk.ukim.finki.performance_review.model.Comment;
import mk.ukim.finki.performance_review.model.dto.CommentsDto;
import mk.ukim.finki.performance_review.model.dto.CommentsForTaskDto;
import mk.ukim.finki.performance_review.model.projections.CommentProjection;

import java.util.List;
import java.util.Optional;

public interface CommentService {

    Optional<Comment> create(String username, Long taskId, String comment);

    Optional<Comment> edit(Long id, String comment);

    Optional<Comment> delete(Long id);

    Optional<Comment> findById(Long id);

    List<Comment> listCommentsForATask(Long taskId);

    List<Comment> listCommentsByDateForATask(String date, Long taskId);

    List<Comment> listCommentsByTaskAndUser(Long taskId, String username);

    List<Comment> listCommentsByDateForTaskAndUser(Long taskId, String username, String date);

    List<CommentsDto> showCommentsDto();

    List<Comment> listAll();

    List<CommentsForTaskDto> findByCommentProjection(Long id);
}
