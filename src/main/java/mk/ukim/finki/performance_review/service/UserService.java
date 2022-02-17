package mk.ukim.finki.performance_review.service;

import mk.ukim.finki.performance_review.model.Task;
import mk.ukim.finki.performance_review.model.enumerations.TaskStatus;
import mk.ukim.finki.performance_review.model.User;

import java.util.List;

public interface UserService {

    User login(String username, String password);

    User register(String name, String surname, String username, String password, String repeatedPassword);

    User edit(String username, String name, String surname);

    List<Task> listAssignedTasks(String username);

    List<Task> listOwnedTasks(String username);

    void delete(String username);

    List<Task> listTasksByStatus(String username, TaskStatus status);

    List<User> listAll();

    User findByUsername(String username);

    List<Integer> findNumberOfTasks(String username);

    List<Integer> findNumberOfTasksByType(String username, String type);

    List<Integer> filterByDate(String username, String dateFrom, String dateTo);
}
