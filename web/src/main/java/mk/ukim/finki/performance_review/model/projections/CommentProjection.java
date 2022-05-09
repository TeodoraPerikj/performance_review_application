package mk.ukim.finki.performance_review.model.projections;

import mk.ukim.finki.performance_review.model.Task;
import mk.ukim.finki.performance_review.model.User;

import java.time.LocalDateTime;

public interface CommentProjection {

    Long getId();

    String getComment();

    LocalDateTime getDateTime();

    Long getTaskId();

}
