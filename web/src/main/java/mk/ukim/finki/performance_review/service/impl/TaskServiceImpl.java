package mk.ukim.finki.performance_review.service.impl;

import mk.ukim.finki.performance_review.model.Comment;
import mk.ukim.finki.performance_review.model.Task;
import mk.ukim.finki.performance_review.model.User;
import mk.ukim.finki.performance_review.model.dto.*;
import mk.ukim.finki.performance_review.model.enumerations.TaskStatus;
import mk.ukim.finki.performance_review.model.exceptions.*;
import mk.ukim.finki.performance_review.model.projections.CommentProjection;
import mk.ukim.finki.performance_review.repository.TaskRepository;
import mk.ukim.finki.performance_review.repository.UserRepository;
import mk.ukim.finki.performance_review.service.TaskService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final CommentServiceImpl commentService;

    public TaskServiceImpl(TaskRepository taskRepository, UserRepository userRepository, CommentServiceImpl commentService) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.commentService = commentService;
    }

    @Override
    public Optional<Task> create(String title, String description, String startDate, String dueDate,
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

        long daysToWorkOnTask = java.time.temporal.ChronoUnit.DAYS
                .between( localStartDate.toLocalDate(), localDueDate.toLocalDate()) ;

        if(estimationDays > daysToWorkOnTask)
            throw new EstimationDaysGreaterThanDaysToWorkOnTaskException(estimationDays, daysToWorkOnTask);

        Task task = new Task(title, description, localStartDate, localDueDate, estimationDays, creator, assignees);

        return Optional.of(this.taskRepository.save(task));
    }

    @Override
    public Optional<Task> edit(Long id, String title, String description, String startDate, String dueDate,
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

        long daysToWorkOnTask = java.time.temporal.ChronoUnit.DAYS
                .between( localStartDate.toLocalDate(), localDueDate.toLocalDate()) ;

        if(estimationDays > daysToWorkOnTask)
            throw new EstimationDaysGreaterThanDaysToWorkOnTaskException(estimationDays, daysToWorkOnTask);

        task.setTitle(title);
        task.setDescription(description);
        task.setStartDate(localStartDate);
        task.setDueDate(localDueDate);
        task.setEstimationDays(estimationDays);

        if(assignees.size()>0)
            task.setAssignees(assignees);

        return Optional.of(this.taskRepository.save(task));
    }

    @Override
    public void delete(Long id) {
        Task task = this.taskRepository.findById(id).orElseThrow(TaskNotFoundException::new);

        this.taskRepository.delete(task);
    }

    @Override
    public List<Task> listAll() {
        return this.taskRepository.findAll().stream().distinct().collect(Collectors.toList());
    }

    @Override
    public Optional<Task> findById(Long id) {
        return Optional.of(this.taskRepository.findById(id).orElseThrow(TaskNotFoundException::new));
    }

    @Override
    public Optional<Task> findByTitle(String title) {

        if(title == null || title.isEmpty())
            throw new InvalidArgumentsException();

        return Optional.of(this.taskRepository.findByTitle(title));
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
    public Optional<Task> addAssignee(Long id, String username) {

        Task task = this.taskRepository.findById(id).orElseThrow(TaskNotFoundException::new);

        User user = this.userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);

        List<User> users = task.getAssignees().stream().distinct().collect(Collectors.toList());

        if(users.stream().distinct().filter(user1 -> user1.getUsername().equals(user.getUsername()))
                .collect(Collectors.toList()).size() > 0)
            throw new UserAlreadyAssignedException();

        users.add(user);
        task.setAssignees(users);

        return Optional.of(this.taskRepository.save(task));
    }

    @Override
    public Optional<Task> changeStatus(Long id, TaskStatus status) {

        Task task = this.taskRepository.findById(id).orElseThrow(TaskNotFoundException::new);

        task.setStatus(status);

        return Optional.of(this.taskRepository.save(task));
    }

    @Override
    public Optional<TaskInfoDto> showTaskInfo(Long id, String username) {

        Task task = null;

        if(this.findById(id).isPresent())
            task = this.findById(id).get();

        String textAndType = this.filter(task, username);
        List<User> assigned = task.getAssignees().stream().distinct().collect(Collectors.toList());

        List<String> assignedUsers = new ArrayList<>();

        for(User user : assigned){
            assignedUsers.add(user.getUsername());
        }

        TaskInfoDto taskInfoDto = new TaskInfoDto(task, textAndType, assignedUsers);

        return Optional.of(taskInfoDto);
    }

    @Override
    public Optional<CopyOfTaskDto> getEditTask(Long id) {

        Task task = null;

        if(this.findById(id).isPresent())
            task = this.findById(id).get();

        String title = task.getTitle();
        String description = task.getDescription();
        LocalDateTime startDate = task.getStartDate();
        LocalDateTime dueDate = task.getDueDate();
        Integer estimationDays = task.getEstimationDays();

        List<String> assignees = new ArrayList<>();

         for(User user : task.getAssignees().stream().distinct().collect(Collectors.toList()))
             assignees.add(user.getUsername());

        CopyOfTaskDto copyOfTaskDto = new CopyOfTaskDto(title, description, startDate, dueDate,
                estimationDays, assignees);

        return Optional.of(copyOfTaskDto);
    }

    @Override
    public Optional<WorkOnTaskDto> showWorkOnTaskPage(Long id) {
        Task task = null;

        if(this.findById(id).isPresent())
            task =this.findById(id).get();

        if(task.getStatus().equals(TaskStatus.TODO)){
            if(this.changeStatus(id, TaskStatus.InProgress).isPresent())
                task = this.changeStatus(id, TaskStatus.InProgress).get();
        }

        List<User> assignedUsers = task.getAssignees().stream().distinct().collect(Collectors.toList());

        List<String> assignees = new ArrayList<>();

        for(User user:assignedUsers)
            assignees.add(user.getUsername());

       // List<Comment> commentsForTask = task.getComments().stream().distinct().collect(Collectors.toList());

//        List<String> comments = new ArrayList<>();
//
//        for(Comment comment:commentsForTask)
//            comments.add(comment.getComment());

        List<CommentsForTaskDto> comments = this.commentService.findByCommentProjection(id);

        WorkOnTaskDto workOnTaskDto = new WorkOnTaskDto(task, assignees, comments);

        return Optional.of(workOnTaskDto);
    }

    @Override
    public Optional<AllUsersAndTasksDto> getHelpPage() {
        List<User> users = this.userRepository.findAll();
        List<Task> tasks = this.taskRepository.findAll();

        AllUsersAndTasksDto allUsersAndTasksDto = new AllUsersAndTasksDto(users, tasks);

        return Optional.of(allUsersAndTasksDto);
    }

    @Override
    public List<TasksDto> showTasksDto() {
        List<TasksDto> tasksDtos = new ArrayList<>();

        for (Task task:this.taskRepository.findAll()) {
            tasksDtos.add(new TasksDto(task.getTitle(), task.getDescription()));
        }

        return tasksDtos;
    }

    @Override
    public Optional<TaskDto> createTaskDto(String title, String description, String startDate,
                                           String dueDate, Integer estimationDays, List<User> assignees) {

        LocalDateTime localStartDate = LocalDateTime.parse(startDate);
        LocalDateTime localDueDate = LocalDateTime.parse(dueDate);

      //  TaskDto taskDto = new TaskDto(title, description, localStartDate, localDueDate, days, assignees);

        return Optional.empty();
    }

    @Override
    public List<Task> findFirst5() {
        List<Task> tasks = new ArrayList<>();

        for(Task task : this.taskRepository.findAll()){
            tasks.add(task);

            if(tasks.size() == 5)
                break;
        }
        return tasks;
    }

    @Override
    public Page<EachTaskDto> findAllWithPagination(Pageable pageable) {
       Page<Task> tasks = this.taskRepository.findAll(pageable);

       List<EachTaskDto> eachTaskDtos = null;

       for(Task task : tasks){

           List<User> users = task.getAssignees().stream().distinct().collect(Collectors.toList());
           List<String> assignees = new ArrayList<>();

           for(User user : users){
               assignees.add(user.getUsername());
           }

           String creator = task.getCreator().getUsername();

           List<CommentsForTaskDto> comments = this.commentService.findByCommentProjection(task.getId());

           EachTaskDto eachTaskDto = new EachTaskDto(task.getId(), task.getTitle(), task.getDescription(), task.getStartDate(),
                   task.getDueDate(), task.getEstimationDays(), task.getStatus(), assignees, creator, comments);

           eachTaskDtos.add(eachTaskDto);
       }
       Page<EachTaskDto> allTasks = (Page<EachTaskDto>) eachTaskDtos;

       return allTasks;
    }

    @Override
    public List<EachTaskDto> findEachTask() {
        List<Task> tasks = this.taskRepository.findAll();
        List<EachTaskDto> eachTaskDtos = new ArrayList<>();

        for(Task task : tasks){

            List<User> users = task.getAssignees().stream().distinct().collect(Collectors.toList());
            List<String> assignees = new ArrayList<>();

            for(User user : users){
                assignees.add(user.getUsername());
            }

            String creator = task.getCreator().getUsername();

            List<CommentsForTaskDto> comments = this.commentService.findByCommentProjection(task.getId());

            EachTaskDto eachTaskDto = new EachTaskDto(task.getId(), task.getTitle(), task.getDescription(), task.getStartDate(),
                    task.getDueDate(), task.getEstimationDays(), task.getStatus(), assignees, creator, comments);

            eachTaskDtos.add(eachTaskDto);
        }
        return eachTaskDtos;
    }

    private boolean checkArgumentsValidity(String title, String description, String startDate, String dueDate,
                                           Integer estimationDays, String username){

        return title != null && !title.isEmpty() && description != null && !description.isEmpty() && startDate != null
                && !startDate.isEmpty() && dueDate != null && !dueDate.isEmpty() && estimationDays != null
                && username != null && !username.isEmpty();
    }

}
