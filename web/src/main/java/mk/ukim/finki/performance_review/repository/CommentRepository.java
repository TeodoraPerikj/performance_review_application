package mk.ukim.finki.performance_review.repository;

import mk.ukim.finki.performance_review.model.Comment;
import mk.ukim.finki.performance_review.model.Task;
import mk.ukim.finki.performance_review.model.User;
import mk.ukim.finki.performance_review.model.projections.CommentProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByTask(Task task);

    List<Comment> findByTaskAndUser(Task task, User user);

    @Query(value = "select c.id, c.comment, c.date_time, c.user_username, c.task_id from Comment c", nativeQuery = true)
    List<CommentProjection> findByProjection();

//    @Query("select c.id, c.comment, c.user, c.dateTime from Comment c where c.id = id")
//    CommentProjection findByProjection(Long id);
}
