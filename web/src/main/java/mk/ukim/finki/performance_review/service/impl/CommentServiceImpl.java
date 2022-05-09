package mk.ukim.finki.performance_review.service.impl;

import mk.ukim.finki.performance_review.model.Comment;
import mk.ukim.finki.performance_review.model.Task;
import mk.ukim.finki.performance_review.model.User;
import mk.ukim.finki.performance_review.model.dto.CommentsDto;
import mk.ukim.finki.performance_review.model.dto.CommentsForTaskDto;
import mk.ukim.finki.performance_review.model.exceptions.CommentNotFoundException;
import mk.ukim.finki.performance_review.model.exceptions.InvalidArgumentsException;
import mk.ukim.finki.performance_review.model.exceptions.TaskNotFoundException;
import mk.ukim.finki.performance_review.model.exceptions.UserNotFoundException;
import mk.ukim.finki.performance_review.model.projections.CommentProjection;
import mk.ukim.finki.performance_review.repository.CommentRepository;
import mk.ukim.finki.performance_review.repository.TaskRepository;
import mk.ukim.finki.performance_review.repository.UserRepository;
import mk.ukim.finki.performance_review.service.CommentService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    public Optional<Comment> create(String username, Long taskId, String comment) {

        User user = this.userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);

        Task task = this.taskRepository.findById(taskId).orElseThrow(TaskNotFoundException::new);

        if(comment == null || comment.isEmpty())
            throw new InvalidArgumentsException();

        LocalDateTime localDateTime = LocalDateTime.now();

        Comment newComment = new Comment(user, task, comment, localDateTime);

        return Optional.of(this.commentRepository.save(newComment));
    }

    @Override
    public Optional<Comment> edit(Long id, String comment) {

        Comment editComment = this.commentRepository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException(id));

        if(comment == null || comment.isEmpty())
            throw new InvalidArgumentsException();

        LocalDateTime localDateTime = LocalDateTime.now();

        editComment.setComment(comment);
        editComment.setDateTime(localDateTime);

        return Optional.of(this.commentRepository.save(editComment));
    }

    @Override
    public Optional<Comment> delete(Long id) {
        Comment comment = this.commentRepository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException(id));

        this.commentRepository.delete(comment);
        return Optional.of(comment);
    }

    @Override
    public Optional<Comment> findById(Long id) {
        return Optional.of(this.commentRepository.findById(id))
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

    @Override
    public List<CommentsDto> showCommentsDto() {
        List<CommentsDto> commentsDtos = new ArrayList<>();

        for(Comment comment:this.commentRepository.findAll()){
            commentsDtos.add(new CommentsDto(comment.getComment(),comment.getTask().getTitle()));
        }

        return commentsDtos;
    }

    @Override
    public List<Comment> listAll() {
        return this.commentRepository.findAll();
    }

    @Override
    public List<CommentsForTaskDto> findByCommentProjection(Long id) {

        List<CommentsForTaskDto> comments = new ArrayList<>();

        for (Comment comment : this.commentRepository.findAll()) {

            if (comment.getTask().getId().equals(id)) {
                comments.add(new CommentsForTaskDto(comment.getId(), comment.getComment(), comment.getUser().getUsername(),
                        comment.getDateTime()));
            }

        }
        return comments;
    }

//        List<CommentProjection> projections = this.commentRepository.findByProjection();
//
//        for(CommentProjection commentProjection:projections){
//            Long commentId = commentProjection.getId();
//            String comment = commentProjection.getComment();
//            LocalDateTime dateTime = commentProjection.getDateTime();
//        }
////        for(CommentProjection commentProjection : this.commentRepository.findByProjection()){
////            if(commentProjection.getTaskId().equals(id)){
////
////                Long commentId = commentProjection.getId();
////                String comment = commentProjection.getComment();
////                LocalDateTime dateTime = commentProjection.getDateTime();
////
////                String username = commentProjection.getUsername();
////
////                comments.add(new CommentsForTaskDto(commentId, comment, username, dateTime));
////            }
////        }
//        return null;


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
