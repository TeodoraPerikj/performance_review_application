package mk.ukim.finki.performance_review.web.rest;

import mk.ukim.finki.performance_review.model.Comment;
import mk.ukim.finki.performance_review.model.Task;
import mk.ukim.finki.performance_review.model.dto.CommentsDto;
import mk.ukim.finki.performance_review.model.dto.WorkOnTaskDto;
import mk.ukim.finki.performance_review.model.enumerations.TaskStatus;
import mk.ukim.finki.performance_review.service.CommentService;
import mk.ukim.finki.performance_review.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/workOnTask")
public class WorkOnTaskRestController {

    private final TaskService taskService;
    private final CommentService commentService;

    public WorkOnTaskRestController(TaskService taskService, CommentService commentService) {
        this.taskService = taskService;
        this.commentService = commentService;
    }

    @GetMapping
    public List<CommentsDto> showCommentsDto(){
        return this.commentService.showCommentsDto();
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkOnTaskDto> showWorkOnTaskPage2(@PathVariable Long id){

        return this.taskService.showWorkOnTaskPage(id)
                .map(workOnTaskDto -> ResponseEntity.ok().body(workOnTaskDto))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PostMapping("/{id}/finishTask")
    public ResponseEntity<Task> finishTask2(@PathVariable Long id){

        return this.taskService.changeStatus(id, TaskStatus.Done)
                .map(task -> ResponseEntity.ok().body(task))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PostMapping("/{id}/cancelTask")
        public ResponseEntity<Task> cancelTask2(@PathVariable Long id){

        return this.taskService.changeStatus(id, TaskStatus.Canceled)
                .map(task -> ResponseEntity.ok().body(task))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/{id}/deleteComment")
    public ResponseEntity deleteComment2(@PathVariable Long id){
        this.commentService.delete(id);

        if(this.commentService.delete(id).isPresent())
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/leaveComment")
    public ResponseEntity<Comment> addComment2(@PathVariable Long id, @RequestParam String comment, HttpServletRequest request){

        String username = request.getRemoteUser();

        return this.commentService.create(username, id, comment)
                .map(comment1 -> ResponseEntity.ok().body(comment1))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

}
