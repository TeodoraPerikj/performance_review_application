package mk.ukim.finki.performance_review.web;

import mk.ukim.finki.performance_review.model.Comment;
import mk.ukim.finki.performance_review.model.Task;
import mk.ukim.finki.performance_review.model.User;
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
import java.util.stream.Collectors;

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
                                     Model model, HttpServletRequest request){

        if(error != null && !error.isEmpty()){
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }

        Task task = this.taskService.findById(id);

        request.getSession().setAttribute("workTask", task);

        if(task.getStatus().equals(TaskStatus.TODO))
            task = this.taskService.changeStatus(id, TaskStatus.InProgress);

        List<User> assignedUsers = task.getAssignees().stream().distinct().collect(Collectors.toList());

        List<Comment> commentsForTask = task.getComments().stream().distinct().collect(Collectors.toList());

        model.addAttribute("workTask", task);
        model.addAttribute("assignedUsers", assignedUsers);
        model.addAttribute("commentsForTask", commentsForTask);

        return "workOnTask";
    }

    @PostMapping("/{id}/finishTask")
    public String finishTask(@PathVariable Long id){

        try {
            this.taskService.changeStatus(id, TaskStatus.Done);
        } catch (TaskNotFoundException exception){
            String url = "/workOnTask/"+id+"?error="+exception.getMessage();
            return "redirect:"+url;
        }

        String dateFinished = LocalDate.now().toString();

        String url = "/taskInfo/"+id+"?dateFinished="+dateFinished;
        return "redirect:"+url;
    }

    @PostMapping("/{id}/cancelTask")
    public String cancelTask(@PathVariable Long id){

        try {
            this.taskService.changeStatus(id, TaskStatus.Canceled);
        } catch (TaskNotFoundException exception){
            String url = "/workOnTask/"+id+"?error="+exception.getMessage();
            return "redirect:"+url;
        }

        return "redirect:/tasks";
    }

    @PostMapping("/{id}/deleteComment")
    public String deleteComment(@PathVariable Long id, @SessionAttribute Task workTask){

        Comment comment;

        try {
            comment = this.commentService.delete(id);
        }catch (CommentNotFoundException exception){

            String url = "/workOnTask/"+workTask.getId()+"?error="+exception.getMessage();
            return "redirect:"+url;
        }

        Long taskId = comment.getTask().getId();

        return "redirect:/workOnTask/"+taskId;
    }

    @PostMapping("/{id}/leaveComment")
    public String addComment(@PathVariable Long id, @RequestParam String comment, HttpServletRequest request){

        String username = request.getRemoteUser();

        try {
            this.commentService.create(username, id, comment);
        }catch (TaskNotFoundException | UserNotFoundException exception){
            String url = "/workOnTask/"+id+"?error="+exception.getMessage();
            return "redirect:"+url;
        }

        return "redirect:/workOnTask/"+id;
    }

}
