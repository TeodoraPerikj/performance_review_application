package mk.ukim.finki.performance_review.service.impl;

import mk.ukim.finki.performance_review.model.Task;
import mk.ukim.finki.performance_review.model.User;
import mk.ukim.finki.performance_review.model.dto.*;
import mk.ukim.finki.performance_review.model.enumerations.TaskStatus;
import mk.ukim.finki.performance_review.model.exceptions.*;
import mk.ukim.finki.performance_review.repository.TaskRepository;
import mk.ukim.finki.performance_review.repository.UserRepository;
import mk.ukim.finki.performance_review.service.TaskService;
import mk.ukim.finki.performance_review.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final PasswordEncoder passwordEncoder;
    private final TaskService taskService;

    public UserServiceImpl(UserRepository userRepository, TaskRepository taskRepository,
                           PasswordEncoder passwordEncoder, TaskService taskService) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
        this.passwordEncoder = passwordEncoder;
        this.taskService = taskService;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return (UserDetails) this.userRepository.findByUsername(s).orElseThrow(()->new UsernameNotFoundException(s));
    }

    @Override
    public Optional<User> login(String username, String password) {
        if(username == null || username.isEmpty() || password == null || password.isEmpty())
            throw new InvalidArgumentsException();

        Optional<User> user = this.userRepository.findByUsernameAndPassword(username, password);

        return Optional.of(user)
                .orElseThrow(InvalidUserCredentialsException::new);
    }

    @Override
    public Optional<User>  register(String name, String surname, String username, String password, String repeatedPassword) {
        if(username == null || username.isEmpty() || password == null || password.isEmpty())
            throw new InvalidArgumentsException();

        if(!password.equals(repeatedPassword))
            throw new PasswordsDoNotMatchException();

        if(this.userRepository.findByUsername(username).isPresent())
            throw new UsernameAlreadyExistsException(username);

        User user = new User(name, surname, username, passwordEncoder.encode(password));

        return Optional.of(this.userRepository.save(user));
    }

    @Override
    public Optional<User> edit(String username, String name, String surname) {
        User user = this.userRepository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);

        user.setName(name);
        user.setSurname(surname);

        return Optional.of(this.userRepository.save(user));
    }

    @Override
    public List<Task> listAssignedTasks(String username) {
        User user = this.userRepository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);

        return user.getTaskAssigned().stream().distinct().collect(Collectors.toList());

    }

    @Override
    public List<Task> listOwnedTasks(String username) {
        User user = this.userRepository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);

        return user.getTaskOwned().stream().distinct().collect(Collectors.toList());
    }

    @Override
    public Optional<User>  delete(String username) {
        User user = this.userRepository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);

        this.userRepository.delete(user);

        return Optional.of(user);
    }

    @Override
    public List<Task> listTasksByStatus(String username, TaskStatus status) {

        User user = this.userRepository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);

        List<Task> tasksByUser = findTasksByUser(user.getUsername())
                .stream().distinct().collect(Collectors.toList());

        return this.findTasksByStatus(status, tasksByUser).stream().distinct().collect(Collectors.toList());
    }

    @Override
    public List<User> listAll() {
        return this.userRepository.findAll();
    }

    @Override
    public Optional<User> findByUsername(String username) {
        Optional<User> user = this.userRepository.findByUsername(username);
        return Optional.of(user).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public List<Integer> findNumberOfTasks(String username) {

        User user = this.userRepository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);

        List<Integer> numberOfTasks = new ArrayList<>();

        List<Task> allTasks = user.getTaskAssigned().stream().distinct().collect(Collectors.toList());

        Integer TODOTasks = this.findTasksByStatus(TaskStatus.TODO, allTasks)
                .stream().distinct().collect(Collectors.toList()).size();
        Integer openTasks = this.findTasksByStatus(TaskStatus.InProgress, allTasks)
                .stream().distinct().collect(Collectors.toList()).size();
        Integer doneTasks = this.findTasksByStatus(TaskStatus.Done, allTasks)
                .stream().distinct().collect(Collectors.toList()).size();
        Integer canceledTasks = this.findTasksByStatus(TaskStatus.Canceled, allTasks)
                .stream().distinct().collect(Collectors.toList()).size();

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

        List<Task> allTasks = user.getTaskAssigned().stream().distinct().collect(Collectors.toList());

        Integer TODOTasks = 0;
        Integer openTasks = 0;
        Integer doneTasks = 0;
        Integer canceledTasks = 0;

        LocalDateTime timeNow = LocalDateTime.now();

        if(type.equals("Monthly")){

            TODOTasks = this.findTasksByStatus(TaskStatus.TODO, allTasks).stream().distinct()
                    .filter(task -> task.getStartDate().getMonth().equals(timeNow.getMonth()))
                    .collect(Collectors.toList()).size();

            openTasks = this.findTasksByStatus(TaskStatus.InProgress, allTasks).stream().distinct()
                    .filter(task -> task.getStartDate().getMonth().equals(timeNow.getMonth()))
                    .collect(Collectors.toList()).size();

            doneTasks = this.findTasksByStatus(TaskStatus.Done, allTasks).stream().distinct()
                    .filter(task -> task.getStartDate().getMonth().equals(timeNow.getMonth()))
                    .collect(Collectors.toList()).size();

            canceledTasks = this.findTasksByStatus(TaskStatus.Canceled, allTasks).stream().distinct()
                    .filter(task -> task.getStartDate().getMonth().equals(timeNow.getMonth()))
                    .collect(Collectors.toList()).size();

        } else if(type.equals("Yearly")){

            TODOTasks = filterByYear(this.findTasksByStatus(TaskStatus.TODO, allTasks)
                    .stream().distinct().collect(Collectors.toList()), timeNow.getYear());

            openTasks = filterByYear(this.findTasksByStatus(TaskStatus.InProgress, allTasks)
                    .stream().distinct().collect(Collectors.toList()), timeNow.getYear());

            doneTasks = filterByYear(this.findTasksByStatus(TaskStatus.Done, allTasks)
                    .stream().distinct().collect(Collectors.toList()), timeNow.getYear());

            canceledTasks = filterByYear(this.findTasksByStatus(TaskStatus.Canceled, allTasks)
                    .stream().distinct().collect(Collectors.toList()), timeNow.getYear());
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

        List<Task> allTasks = user.getTaskAssigned().stream().distinct().collect(Collectors.toList());

        LocalDate startDate = LocalDate.parse(dateFrom);
        LocalDate endDate = LocalDate.parse(dateTo);

        Integer TODOTasks = this.findTasksByStatus(TaskStatus.TODO, allTasks).stream().distinct()
                .filter(task -> (task.getStartDate().toLocalDate().isAfter(startDate)
                                && task.getStartDate().toLocalDate().isBefore(endDate))
                                || task.getStartDate().toLocalDate().isEqual(startDate)
                                || task.getStartDate().toLocalDate().isEqual(endDate)
                )
                .collect(Collectors.toList()).size();

        Integer openTasks = this.findTasksByStatus(TaskStatus.InProgress, allTasks).stream().distinct()
                .filter(task -> (task.getStartDate().toLocalDate().isAfter(startDate)
                        && task.getStartDate().toLocalDate().isBefore(endDate))
                        || task.getStartDate().toLocalDate().isEqual(startDate)
                        || task.getStartDate().toLocalDate().isEqual(endDate)
                )
                .collect(Collectors.toList()).size();

        Integer doneTasks = this.findTasksByStatus(TaskStatus.Done, allTasks).stream().distinct()
                 .filter(task -> (task.getStartDate().toLocalDate().isAfter(startDate)
                        && task.getStartDate().toLocalDate().isBefore(endDate))
                        || task.getStartDate().toLocalDate().isEqual(startDate)
                        || task.getStartDate().toLocalDate().isEqual(endDate)
                 )
                .collect(Collectors.toList()).size();

        Integer canceledTasks = this.findTasksByStatus(TaskStatus.Canceled, allTasks).stream().distinct()
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

    @Override
    public List<String> calculatePerformanceAndRating(List<Integer> numberOfTasks) {

        Double calculatedPerformance;

        Integer numberOfTasksAssigned = numberOfTasks.get(0)+numberOfTasks.get(1)
                +numberOfTasks.get(2)+numberOfTasks.get(3);

        Integer numberOfDoneTasks = numberOfTasks.get(2);

        calculatedPerformance = Double.valueOf(numberOfDoneTasks)/Double.valueOf(numberOfTasksAssigned);
        calculatedPerformance*=100;

        String rate;

        if(calculatedPerformance<50)
            rate = "Underrated";
        else if(calculatedPerformance > 50)
            rate = "Overrated";
        else if(calculatedPerformance.equals(50.0))
            rate = "Fine";
        else rate = "Do not have assigned tasks to see performance";

        List<String> calculatedPerformanceAndRating = new ArrayList<>();
        calculatedPerformanceAndRating.add(rate);
        calculatedPerformanceAndRating.add(calculatedPerformance.toString());

        return calculatedPerformanceAndRating;
    }

    @Override
    public List<User> findAssignedUsers(String assignees) {
        List<User> assignedUsers = new ArrayList<>();

        User findUser = null;
        if(this.findByUsername(assignees).isPresent())
            findUser = this.findByUsername(assignees).get();

        assignedUsers.add(findUser);

        return assignedUsers;
    }

    @Override
    public Optional<UserPerformanceDto> showUserPerformance(String chosenUsername, String type,
                                                            String dateFrom, String dateTo) {
        User user = null;

        if(this.findByUsername(chosenUsername).isPresent())
            user = this.findByUsername(chosenUsername).get();

        List<Integer> numberOfTasks;

        if(type.equals("Weekly")){
            numberOfTasks = this.filterByDate(user.getUsername(), dateFrom, dateTo);
        }else {
            numberOfTasks = this.findNumberOfTasksByType(user.getUsername(), type);
        }

        List<String> calculatedPerformanceAndRating = this.calculatePerformanceAndRating(numberOfTasks);

        String calculatedPerformance = String.format("%.2f",Double.valueOf(calculatedPerformanceAndRating.get(1)));

        UserPerformanceDto userPerformanceDto = new UserPerformanceDto(user, numberOfTasks,
                calculatedPerformanceAndRating, Double.valueOf(calculatedPerformance), type);

        return Optional.of(userPerformanceDto);
    }

    @Override
    public Optional<UserTasksDto> showUserTasks(String username) {

        List<Task> myToDoTasks = this.listTasksByStatus(username, TaskStatus.TODO);
        List<Task> myInProgressTasks = this.listTasksByStatus(username, TaskStatus.InProgress);
        List<Task> myDoneTasks = this.listTasksByStatus(username, TaskStatus.Done);
        List<Task> myCanceledTasks = this.listTasksByStatus(username, TaskStatus.Canceled);
        List<Task> ownedTasks = this.taskService.findByCreator(username);

        List<Integer> numberOfTasks = this.findNumberOfTasks(username);

        UserTasksDto userTasksDto = new UserTasksDto(myToDoTasks, myInProgressTasks, myDoneTasks, myCanceledTasks,
                ownedTasks, username, numberOfTasks);

        return Optional.of(userTasksDto);
    }

    @Override
    public List<UsersDto> showUsersDto() {

        List<UsersDto> usersDtos = new ArrayList<>();

        for(User user:this.userRepository.findAll()){
            usersDtos.add(new UsersDto(user.getName(), user.getSurname(), user.getUsername()));
        }

        return usersDtos;
    }

    @Override
    public Optional<UserLoginDto> loginUserDto(String username, String password) {
        return Optional.of(new UserLoginDto(username, password));
    }

    @Override
    public MyUserTasksDto findTasksForTheUser(String username) {
        List<Task> myToDoTasks;
        List<Task> myInProgressTasks;
        List<Task> myDoneTasks;
        List<Task> myCanceledTasks;
        List<Task> ownedTasks;

        myToDoTasks = this.listTasksByStatus(username, TaskStatus.TODO);
        myInProgressTasks = this.listTasksByStatus(username, TaskStatus.InProgress);
        myDoneTasks = this.listTasksByStatus(username, TaskStatus.Done);
        myCanceledTasks = this.listTasksByStatus(username, TaskStatus.Canceled);
        ownedTasks = this.taskService.findByCreator(username);

        List<Integer> numberOfTasks = this.findNumberOfTasks(username);

        MyUserTasksDto myUserTasksDto = new MyUserTasksDto(myToDoTasks, myInProgressTasks,
                myDoneTasks, myCanceledTasks, ownedTasks, numberOfTasks);

        return myUserTasksDto;
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

        return tasksByStatus.stream().distinct().collect(Collectors.toList());
    }

    private List<Task> findTasksByUser(String username){

        List<Task> tasksByUser = new ArrayList<>();

        for(Task task : this.taskRepository.findAll()){

            if (task.getAssignees().stream().distinct().filter(assignee -> assignee.getUsername().equals(username))
                    .collect(Collectors.toList()).size() > 0)
                tasksByUser.add(task);
        }

        return tasksByUser.stream().distinct().collect(Collectors.toList());
    }
}
