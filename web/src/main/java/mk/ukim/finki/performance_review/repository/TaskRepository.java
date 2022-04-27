package mk.ukim.finki.performance_review.repository;

import mk.ukim.finki.performance_review.model.Task;
import mk.ukim.finki.performance_review.model.User;
import mk.ukim.finki.performance_review.model.enumerations.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    Task findByTitle(String title);

    List<Task> findByStatusAndCreator(TaskStatus status, User creator);

    List<Task> findByCreator(User creator);

    List<Task> findByStatus(TaskStatus status);
}
