package mk.ukim.finki.performance_review.service.impl;

import mk.ukim.finki.performance_review.model.Comment;
import mk.ukim.finki.performance_review.model.Task;
import mk.ukim.finki.performance_review.model.User;
import mk.ukim.finki.performance_review.model.exceptions.CommentNotFoundException;
import mk.ukim.finki.performance_review.model.exceptions.InvalidArgumentsException;
import mk.ukim.finki.performance_review.model.exceptions.TaskNotFoundException;
import mk.ukim.finki.performance_review.model.exceptions.UserNotFoundException;
import mk.ukim.finki.performance_review.repository.CommentRepository;
import mk.ukim.finki.performance_review.repository.TaskRepository;
import mk.ukim.finki.performance_review.repository.UserRepository;
import mk.ukim.finki.performance_review.service.CommentService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    public CommentServiceImpl(CommentRepository commentRepository, UserRepository userRepository,
                              TaskRepository taskRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    @Override
    public Comment create(String username, Long taskId, String comment) {

        User user = this.userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);

        Task task = this.taskRepository.findById(taskId).orElseThrow(TaskNotFoundException::new);

        if(comment == null || comment.isEmpty())
            throw new InvalidArgumentsException();

        LocalDateTime localDateTime = LocalDateTime.now();

        Comment newComment = new Comment(user, task, comment, localDateTime);

        return this.commentRepository.save(newComment);
    }

    @Override
    public Comment edit(Long id, String comment) {

        Comment editComment = this.commentRepository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException(id));

        if(comment == null || comment.isEmpty())
            throw new InvalidArgumentsException();

        LocalDateTime localDateTime = LocalDateTime.now();

        editComment.setComment(comment);
        editComment.setDateTime(localDateTime);

        return this.commentRepository.save(editComment);
    }

    @Override
    public Comment delete(Long id) {
        Comment comment = this.commentRepository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException(id));

        this.commentRepository.delete(comment);
        return comment;
    }

    @Override
    public Comment findById(Long id) {
        return this.commentRepository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException(id));
    }

    @Override
    public List<Comment> listCommentsForATask(Long taskId) {

        Task task = this.taskRepository.findById(taskId).orElseThrow(TaskNotFoundException::new);

        return this.commentRepository.findByTask(task).stream().distinct().collect(Collectors.toList());
    }

    @Override
    public List<Comment> listCommentsByDateForATask(String date, Long taskId) {

        Task task = this.taskRepository.findById(taskId).orElseThrow(TaskNotFoundException::new);

        LocalDateTime localDateTime = LocalDateTime.parse(date);

        LocalDate localDate = localDateTime.toLocalDate();

        List<Comment> foundCommentsByTask = this.commentRepository.findByTask(task)
                .stream().distinct().collect(Collectors.toList());

        return this.findCommentsByDate(localDate, foundCommentsByTask).stream().distinct().collect(Collectors.toList());
    }

    @Override
    public List<Comment> listCommentsByTaskAndUser(Long taskId, String username) {

        Task task = this.taskRepository.findById(taskId).orElseThrow(TaskNotFoundException::new);

        User user = this.userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);

        return this.commentRepository.findByTaskAndUser(task, user).stream().distinct().collect(Collectors.toList());
    }

    @Override
    public List<Comment> listCommentsByDateForTaskAndUser(Long taskId, String username, String date) {

        Task task = this.taskRepository.findById(taskId).orElseThrow(TaskNotFoundException::new);

        User user = this.userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);

        LocalDateTime localDateTime = LocalDateTime.parse(date);
        LocalDate localDate = localDateTime.toLocalDate();

        List<Comment> foundCommentsForTaskAndUser = this.commentRepository.findByTaskAndUser(task, user)
                .stream().distinct().collect(Collectors.toList());

        return this.findCommentsByDate(localDate, foundCommentsForTaskAndUser).stream().distinct().collect(Collectors.toList());
    }

    private List<Comment> findCommentsByDate(LocalDate localDate, List<Comment> comments){

        List<Comment> foundComments = new ArrayList<>();

        for(Comment comment : comments){

            LocalDate dateForComment = comment.getDateTime().toLocalDate();

            if(localDate.equals(dateForComment))
                foundComments.add(comment);
        }

        return foundComments.stream().distinct().collect(Collectors.toList());
    }
}
