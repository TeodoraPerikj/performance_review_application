package mk.ukim.finki.performance_review.web;

import mk.ukim.finki.performance_review.model.Comment;
import mk.ukim.finki.performance_review.model.Task;
import mk.ukim.finki.performance_review.model.enumerations.TaskStatus;
import mk.ukim.finki.performance_review.model.exceptions.CommentNotFoundException;
import mk.ukim.finki.performance_review.model.exceptions.TaskNotFoundException;
import mk.ukim.finki.performance_review.model.exceptions.UserNotFoundException;
import mk.ukim.finki.performance_review.service.CommentService;
import mk.ukim.finki.performance_review.service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/workOnTask")
public class WorkOnTaskController {

    private final TaskService taskService;
    private final CommentService commentService;

    public WorkOnTaskController(TaskService taskService, CommentService commentService) {
        this.taskService = taskService;
        this.commentService = commentService;
    }

    @GetMapping("/{id}")
    public String showWorkOnTaskPage(@PathVariable Long id, @RequestParam(required = false) String error,
                                     Model model){

        if(error != null && !error.isEmpty()){
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }

        Task task = this.taskService.findById(id);

        if(task.getStatus().equals(TaskStatus.TODO))
            task = this.taskService.changeStatus(id, TaskStatus.InProgress);

        model.addAttribute("workTask", task);

        return "workOnTask";
    }

    @GetMapping("/{id}/finishTask")
    public String finishTask(@PathVariable Long id){

        try {
            this.taskService.changeStatus(id, TaskStatus.Done);
        } catch (TaskNotFoundException exception){
            return "redirect:/workOnTask/{id}?error="+exception.getMessage();
        }

        String dateFinished = LocalDate.now().toString();

        String url = "redirect:/taskInfo/"+id+"?dateFinished="+dateFinished;
        return url;
    }

    @GetMapping("/{id}/cancelTask")
    public String cancelTask(@PathVariable Long id){

        try {
            this.taskService.changeStatus(id, TaskStatus.Canceled);
        } catch (TaskNotFoundException exception){
            String url = "redirect:/workOnTask/"+id+"?error="+exception.getMessage();
            return url;
        }

        return "redirect:/tasks";
    }

    @PostMapping("/{id}/deleteComment")
    public String deleteComment(@PathVariable Long id){

        Comment comment;

        try {
            comment = this.commentService.delete(id);
        }catch (CommentNotFoundException exception){
            String url = "redirect:/workOnTask/"+id+"?error="+exception.getMessage();
            return url;
        }

        Long taskId = comment.getTask().getId();

        return "redirect:/workOnTask/"+taskId;
    }

    @PostMapping("/{id}/leaveComment")
    public String addComment(@PathVariable Long id, @RequestParam String comment, HttpServletRequest request){

        try {
            this.commentService.create(request.getRemoteUser(), id, comment);
        }catch (TaskNotFoundException | UserNotFoundException exception){
            String url = "redirect:/workOnTask/"+id+"?error="+exception.getMessage();
            return url;
        }

        return "redirect:/workOnTask/"+id;
    }

}
