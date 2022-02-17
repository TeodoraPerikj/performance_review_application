package mk.ukim.finki.performance_review.service.impl;

import mk.ukim.finki.performance_review.model.Task;
import mk.ukim.finki.performance_review.model.enumerations.TaskStatus;
import mk.ukim.finki.performance_review.model.User;
import mk.ukim.finki.performance_review.model.exceptions.*;
import mk.ukim.finki.performance_review.repository.TaskRepository;
import mk.ukim.finki.performance_review.repository.UserRepository;
import mk.ukim.finki.performance_review.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, TaskRepository taskRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User login(String username, String password) {
        if(username == null || username.isEmpty() || password == null || password.isEmpty())
            throw new InvalidArgumentsException();

        return this.userRepository.findByUsernameAndPassword(username, password)
                .orElseThrow(InvalidUserCredentialsException::new);
    }

    @Override
    public User register(String name, String surname, String username, String password, String repeatedPassword) {
        if(username == null || username.isEmpty() || password == null || password.isEmpty())
            throw new InvalidArgumentsException();

        if(!password.equals(repeatedPassword))
            throw new PasswordsDoNotMatchException();

        if(this.userRepository.findByUsername(username).isPresent())
            throw new UsernameAlreadyExistsException(username);

        User user = new User(name, surname, username, passwordEncoder.encode(password));

        return this.userRepository.save(user);
    }

    @Override
    public User edit(String username, String name, String surname) {
        User user = this.userRepository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);

        user.setName(name);
        user.setSurname(surname);

        return this.userRepository.save(user);
    }

    @Override
    public List<Task> listAssignedTasks(String username) {
        User user = this.userRepository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);

        return user.getTaskAssigned();

    }

    @Override
    public List<Task> listOwnedTasks(String username) {
        User user = this.userRepository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);

        return user.getTaskOwned();
    }

    @Override
    public void delete(String username) {
        User user = this.userRepository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);

        this.userRepository.delete(user);
    }

    @Override
    public List<Task> listTasksByStatus(String username, TaskStatus status) {

        User user = this.userRepository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);

        List<Task> tasksByUser = findTasksByUser(user.getUsername());

        return this.findTasksByStatus(status, tasksByUser);
    }

    @Override
    public List<User> listAll() {
        return this.userRepository.findAll();
    }

    @Override
    public User findByUsername(String username) {
        return this.userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public List<Integer> findNumberOfTasks(String username) {

        User user = this.userRepository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);

        List<Integer> numberOfTasks = new ArrayList<>();

        List<Task> allTasks = user.getTaskAssigned();

        Integer TODOTasks = this.findTasksByStatus(TaskStatus.TODO, allTasks).size();
        Integer openTasks = this.findTasksByStatus(TaskStatus.InProgress, allTasks).size();
        Integer doneTasks = this.findTasksByStatus(TaskStatus.Done, allTasks).size();
        Integer canceledTasks = this.findTasksByStatus(TaskStatus.Canceled, allTasks).size();

        numberOfTasks.add(TODOTasks);
        numberOfTasks.add(openTasks);
        numberOfTasks.add(doneTasks);
        numberOfTasks.add(canceledTasks);

        return numberOfTasks;
    }

    @Override
    public List<Integer> findNumberOfTasksByType(String username, String type) {
        User user = this.userRepository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);

        List<Integer> numberOfTasks = new ArrayList<>();

        List<Task> allTasks = user.getTaskAssigned();

        Integer TODOTasks = 0;
        Integer openTasks = 0;
        Integer doneTasks = 0;
        Integer canceledTasks = 0;

        LocalDateTime timeNow = LocalDateTime.now();

        if(type.equals("Monthly")){

            TODOTasks = this.findTasksByStatus(TaskStatus.TODO, allTasks).stream()
                    .filter(task -> task.getStartDate().getMonth().equals(timeNow.getMonth()))
                    .collect(Collectors.toList()).size();

            openTasks = this.findTasksByStatus(TaskStatus.InProgress, allTasks).stream()
                    .filter(task -> task.getStartDate().getMonth().equals(timeNow.getMonth()))
                    .collect(Collectors.toList()).size();

            doneTasks = this.findTasksByStatus(TaskStatus.Done, allTasks).stream()
                    .filter(task -> task.getStartDate().getMonth().equals(timeNow.getMonth()))
                    .collect(Collectors.toList()).size();

            canceledTasks = this.findTasksByStatus(TaskStatus.Canceled, allTasks).stream()
                    .filter(task -> task.getStartDate().getMonth().equals(timeNow.getMonth()))
                    .collect(Collectors.toList()).size();

        } else if(type.equals("Yearly")){

            TODOTasks = filterByYear(this.findTasksByStatus(TaskStatus.TODO, allTasks), timeNow.getYear());

            openTasks = filterByYear(this.findTasksByStatus(TaskStatus.InProgress, allTasks), timeNow.getYear());

            doneTasks = filterByYear(this.findTasksByStatus(TaskStatus.Done, allTasks), timeNow.getYear());

            canceledTasks = filterByYear(this.findTasksByStatus(TaskStatus.Canceled, allTasks), timeNow.getYear());
        }

        numberOfTasks.add(TODOTasks);
        numberOfTasks.add(openTasks);
        numberOfTasks.add(doneTasks);
        numberOfTasks.add(canceledTasks);

        return numberOfTasks;
    }

    @Override
    public List<Integer> filterByDate(String username, String dateFrom, String dateTo) {

        User user = this.userRepository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);

        List<Integer> numberOfTasks = new ArrayList<>();

        List<Task> allTasks = user.getTaskAssigned();

        LocalDate startDate = LocalDate.parse(dateFrom);
        LocalDate endDate = LocalDate.parse(dateTo);

        Integer TODOTasks = this.findTasksByStatus(TaskStatus.TODO, allTasks).stream()
                .filter(task -> (task.getStartDate().toLocalDate().isAfter(startDate)
                                && task.getStartDate().toLocalDate().isBefore(endDate))
                                || task.getStartDate().toLocalDate().isEqual(startDate)
                                || task.getStartDate().toLocalDate().isEqual(endDate)
                )
                .collect(Collectors.toList()).size();

        Integer openTasks = this.findTasksByStatus(TaskStatus.InProgress, allTasks).stream()
                .filter(task -> (task.getStartDate().toLocalDate().isAfter(startDate)
                        && task.getStartDate().toLocalDate().isBefore(endDate))
                        || task.getStartDate().toLocalDate().isEqual(startDate)
                        || task.getStartDate().toLocalDate().isEqual(endDate)
                )
                .collect(Collectors.toList()).size();

        Integer doneTasks = this.findTasksByStatus(TaskStatus.Done, allTasks).stream()
                 .filter(task -> (task.getStartDate().toLocalDate().isAfter(startDate)
                        && task.getStartDate().toLocalDate().isBefore(endDate))
                        || task.getStartDate().toLocalDate().isEqual(startDate)
                        || task.getStartDate().toLocalDate().isEqual(endDate)
                 )
                .collect(Collectors.toList()).size();

        Integer canceledTasks = this.findTasksByStatus(TaskStatus.Canceled, allTasks).stream()
                .filter(task -> (task.getStartDate().toLocalDate().isAfter(startDate)
                        && task.getStartDate().toLocalDate().isBefore(endDate))
                        || task.getStartDate().toLocalDate().isEqual(startDate)
                        || task.getStartDate().toLocalDate().isEqual(endDate)
                )
                .collect(Collectors.toList()).size();

        numberOfTasks.add(TODOTasks);
        numberOfTasks.add(openTasks);
        numberOfTasks.add(doneTasks);
        numberOfTasks.add(canceledTasks);

        return numberOfTasks;
    }

    private Integer filterByYear(List<Task> tasks, int year){

        Integer number = 0;

        for(Task task : tasks){

            if(task.getStartDate().getYear() == year)
                number++;
        }

        return number;
    }

    private List<Task> findTasksByStatus(TaskStatus status, List<Task> tasksByUser){

        List<Task> tasksByStatus = new ArrayList<>();

        for(Task task : tasksByUser){

            if(task.getStatus().equals(status))
                tasksByStatus.add(task);

        }

        return tasksByStatus;
    }

    private List<Task> findTasksByUser(String username){

        List<Task> tasksByUser = new ArrayList<>();

        for(Task task : this.taskRepository.findAll()){

            if (task.getAssignees().stream().filter(assignee -> assignee.getUsername().equals(username))
                    .collect(Collectors.toList()).size() > 0)
                tasksByUser.add(task);
        }

        return tasksByUser;
    }
}
