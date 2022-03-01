package mk.ukim.finki.performance_review.service;

import mk.ukim.finki.performance_review.model.Comment;
import mk.ukim.finki.performance_review.model.Task;
import mk.ukim.finki.performance_review.model.enumerations.TaskStatus;
import mk.ukim.finki.performance_review.model.User;

import java.util.List;

public interface TaskService {

    Task create(String title, String description, String startDate, String dueDate,
                Integer estimationDays, String username, List<User> assignees);

    Task edit(Long id, String title, String description, String startDate, String dueDate,
              Integer estimationDays, List<User> assignees);

    Task delete(Long id);

    List<Task> listAll();

    Task findById(Long id);

    Task findByTitle(String title);

    List<Task> findByStartDate(String startDate);

    List<Task> findByDueDate(String dueDate);

    List<Task> findByStatusAndUser(TaskStatus status, String username);

    List<User> listAssignees(Long id);

    List<Task> findByCreator(String username);

    List<Comment> listComments(Long id);

    List<Task> findByStatus(TaskStatus status);

    String filter(Task task, String username);

    Task addAssignee(Long id, String username);

    Task changeStatus(Long id, TaskStatus status);
}
