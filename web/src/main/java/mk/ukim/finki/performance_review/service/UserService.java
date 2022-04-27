package mk.ukim.finki.performance_review.service;

import mk.ukim.finki.performance_review.model.Task;
import mk.ukim.finki.performance_review.model.User;
import mk.ukim.finki.performance_review.model.dto.*;
import mk.ukim.finki.performance_review.model.enumerations.TaskStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {

    Optional<User> login(String username, String password);

    Optional<User>  register(String name, String surname, String username, String password, String repeatedPassword);

    Optional<User>  edit(String username, String name, String surname);

    List<Task> listAssignedTasks(String username);

    List<Task> listOwnedTasks(String username);

    Optional<User>  delete(String username);

    List<Task> listTasksByStatus(String username, TaskStatus status);

    List<User> listAll();

    Optional<User>  findByUsername(String username);

    List<Integer> findNumberOfTasks(String username);

    List<Integer> findNumberOfTasksByType(String username, String type);

    List<Integer> filterByDate(String username, String dateFrom, String dateTo);

    List<String> calculatePerformanceAndRating(List<Integer> numberOfTasks);

    List<User> findAssignedUsers(String assignees);

    Optional<UserPerformanceDto> showUserPerformance(String chosenUsername, String type,
                                                     String dateFrom, String dateTo);

    Optional<UserTasksDto> showUserTasks(String username);

    List<UsersDto> showUsersDto();

    Optional<UserLoginDto> loginUserDto(String username, String password);

    MyUserTasksDto findTasksForTheUser(String username);
}
