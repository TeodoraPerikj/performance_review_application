package mk.ukim.finki.performance_review.service.impl;

import mk.ukim.finki.performance_review.model.Comment;
import mk.ukim.finki.performance_review.model.Task;
import mk.ukim.finki.performance_review.model.enumerations.TaskStatus;
import mk.ukim.finki.performance_review.model.User;
import mk.ukim.finki.performance_review.model.exceptions.*;
import mk.ukim.finki.performance_review.repository.TaskRepository;
import mk.ukim.finki.performance_review.repository.UserRepository;
import mk.ukim.finki.performance_review.service.TaskService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskServiceImpl(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Task create(String title, String description, String startDate, String dueDate,
                       Integer estimationDays, String username, List<User> assignees) {

        if(!checkArgumentsValidity(title, description, startDate, dueDate, estimationDays, username))
            throw new InvalidArgumentsException();

        if(estimationDays <= 0)
            throw new EstimationDaysOutOfRangeException(estimationDays);

        User creator = this.userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);

        LocalDateTime localStartDate = LocalDateTime.parse(startDate);
        LocalDateTime localDueDate = LocalDateTime.parse(dueDate);

        if(localStartDate.isAfter(localDueDate))
            throw new LocalDateTimeException();

        Task task = new Task(title, description, localStartDate, localDueDate, estimationDays, creator, assignees);

        return this.taskRepository.save(task);
    }

    @Override
    public Task edit(Long id, String title, String description, String startDate, String dueDate,
                     Integer estimationDays, List<User> assignees) {

        Task task = this.taskRepository.findById(id).orElseThrow(TaskNotFoundException::new);

        if(title == null || title.isEmpty() || description == null || description.isEmpty() || startDate == null
                || startDate.isEmpty() || dueDate == null || dueDate.isEmpty() || estimationDays == null)
            throw new InvalidArgumentsException();

        if(estimationDays <= 0)
            throw new EstimationDaysOutOfRangeException(estimationDays);

        LocalDateTime localStartDate = LocalDateTime.parse(startDate);
        LocalDateTime localDueDate = LocalDateTime.parse(dueDate);

        if(localStartDate.isAfter(localDueDate))
            throw new LocalDateTimeException();

        task.setTitle(title);
        task.setDescription(description);
        task.setStartDate(localStartDate);
        task.setDueDate(localDueDate);
        task.setEstimationDays(estimationDays);

        if(assignees.size()>0)
            task.setAssignees(assignees);

        return this.taskRepository.save(task);
    }

    @Override
    public Task delete(Long id) {
        Task task = this.taskRepository.findById(id).orElseThrow(TaskNotFoundException::new);

        this.taskRepository.delete(task);

        return task;
    }

    @Override
    public List<Task> listAll() {
        return this.taskRepository.findAll().stream().distinct().collect(Collectors.toList());
    }

    @Override
    public Task findById(Long id) {
        return this.taskRepository.findById(id).orElseThrow(TaskNotFoundException::new);
    }

    @Override
    public Task findByTitle(String title) {

        if(title == null || title.isEmpty())
            throw new InvalidArgumentsException();

        return this.taskRepository.findByTitle(title);
    }

    @Override
    public List<Task> findByStartDate(String startDate) {

        LocalDate localDate = LocalDate.parse(startDate);

        List<Task> tasksByStartDate = new ArrayList<>();

        for(Task task : this.taskRepository.findAll()){

            LocalDate taskStartDate = task.getStartDate().toLocalDate();

            if(taskStartDate.equals(localDate))
                tasksByStartDate.add(task);

        }

        return tasksByStartDate.stream().distinct().collect(Collectors.toList());
    }

    @Override
    public List<Task> findByDueDate(String dueDate) {

        List<Task> tasksByDueDate = new ArrayList<>();

        LocalDate localDate = LocalDate.parse(dueDate);

        for(Task task : this.taskRepository.findAll()){

            LocalDate taskDueDate = task.getDueDate().toLocalDate();

            if(taskDueDate.equals(localDate))
                tasksByDueDate.add(task);

        }

        return tasksByDueDate.stream().distinct().collect(Collectors.toList());
    }

    @Override
    public List<Task> findByStatusAndUser(TaskStatus status, String username) {

        User creator = this.userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);

        return this.taskRepository.findByStatusAndCreator(status, creator).stream().distinct().collect(Collectors.toList());
    }

    @Override
    public List<User> listAssignees(Long id) {
        Task task = this.taskRepository.findById(id).orElseThrow(TaskNotFoundException::new);

        return task.getAssignees().stream().distinct().collect(Collectors.toList());
    }

    @Override
    public List<Task> findByCreator(String username) {
        User creator = this.userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);

        return this.taskRepository.findByCreator(creator).stream().distinct().collect(Collectors.toList());
    }

    @Override
    public List<Comment> listComments(Long id) {
        Task task = this.taskRepository.findById(id).orElseThrow(TaskNotFoundException::new);

        return task.getComments().stream().distinct().collect(Collectors.toList());
    }

    @Override
    public List<Task> findByStatus(TaskStatus status) {

        if(status == null)
            throw new InvalidArgumentsException();

        return this.taskRepository.findByStatus(status).stream().distinct().collect(Collectors.toList());
    }

    @Override
    public String filter(Task task, String username) {

        User user = this.userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);

        LocalDateTime dateTime = LocalDateTime.now();

        if(task.getStartDate().isAfter(dateTime))
            return "Not opened yet!";
        else if(task.getDueDate().isBefore(dateTime))
            return "Task is closed!";
        else {

            if(task.getStatus().equals(TaskStatus.TODO)){

                if (task.getAssignees().stream().distinct()
                        .filter(assignee -> assignee.getUsername().equals(user.getUsername()))
                        .collect(Collectors.toList()).size() > 0)
                    return "Start Task";

                return "Join Task";
            } else if(task.getStatus().equals(TaskStatus.InProgress)) {

                if (task.getAssignees().stream().distinct()
                        .filter(assignee -> assignee.getUsername().equals(user.getUsername()))
                        .collect(Collectors.toList()).size() > 0)
                    return "Continue Task";

                return "Join Task";
            } else if(task.getStatus().equals(TaskStatus.Done)){
                return "Finished";
            } else {
                return "Task has been canceled";
            }

        }
    }

    @Override
    public Task addAssignee(Long id, String username) {

        Task task = this.taskRepository.findById(id).orElseThrow(TaskNotFoundException::new);

        User user = this.userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);

        List<User> users = task.getAssignees().stream().distinct().collect(Collectors.toList());

        if(users.stream().distinct().filter(user1 -> user1.getUsername().equals(user.getUsername()))
                .collect(Collectors.toList()).size() > 0)
            throw new UserAlreadyAssignedException();

        users.add(user);
        task.setAssignees(users);

        return this.taskRepository.save(task);
    }

    @Override
    public Task changeStatus(Long id, TaskStatus status) {

        Task task = this.taskRepository.findById(id).orElseThrow(TaskNotFoundException::new);

        task.setStatus(status);

        return this.taskRepository.save(task);
    }

    private boolean checkArgumentsValidity(String title, String description, String startDate, String dueDate,
                                           Integer estimationDays, String username){

        return title != null && !title.isEmpty() && description != null && !description.isEmpty() && startDate != null
                && !startDate.isEmpty() && dueDate != null && !dueDate.isEmpty() && estimationDays != null
                && username != null && !username.isEmpty();
    }

}
