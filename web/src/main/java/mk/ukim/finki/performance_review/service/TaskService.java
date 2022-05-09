package mk.ukim.finki.performance_review.service;

import mk.ukim.finki.performance_review.model.Comment;
import mk.ukim.finki.performance_review.model.Task;
import mk.ukim.finki.performance_review.model.User;
import mk.ukim.finki.performance_review.model.dto.*;
import mk.ukim.finki.performance_review.model.enumerations.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TaskService {

    Optional<Task> create(String title, String description, String startDate, String dueDate,
                Integer estimationDays, String username, List<User> assignees);

    Optional<Task> edit(Long id, String title, String description, String startDate, String dueDate,
              Integer estimationDays, List<User> assignees);

    void delete(Long id);

    List<Task> listAll();

    Optional<Task> findById(Long id);

    Optional<Task> findByTitle(String title);

    List<Task> findByStartDate(String startDate);

    List<Task> findByDueDate(String dueDate);

    List<Task> findByStatusAndUser(TaskStatus status, String username);

    List<User> listAssignees(Long id);

    List<Task> findByCreator(String username);

    List<Comment> listComments(Long id);

    List<Task> findByStatus(TaskStatus status);

    String filter(Task task, String username);

    Optional<Task> addAssignee(Long id, String username);

    Optional<Task> changeStatus(Long id, TaskStatus status);

    Optional<TaskInfoDto> showTaskInfo(Long id, String username);

    Optional<CopyOfTaskDto> getEditTask(Long id);

    Optional<WorkOnTaskDto> showWorkOnTaskPage(Long id);

    Optional<AllUsersAndTasksDto> getHelpPage();

    List<TasksDto> showTasksDto();

    Optional<TaskDto> createTaskDto( String title, String description, String startDate,
                                     String dueDate, Integer days, List<User> assignees);

    List<Task> findFirst5();

    Page<EachTaskDto> findAllWithPagination(Pageable pageable);

    List<EachTaskDto> findEachTask();
}
