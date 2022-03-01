package mk.ukim.finki.performance_review.web;

import mk.ukim.finki.performance_review.model.Task;
import mk.ukim.finki.performance_review.model.User;
import mk.ukim.finki.performance_review.model.exceptions.TaskNotFoundException;
import mk.ukim.finki.performance_review.model.exceptions.UserAlreadyAssignedException;
import mk.ukim.finki.performance_review.model.exceptions.UserNotFoundException;
import mk.ukim.finki.performance_review.service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/taskInfo")
public class TaskInfoController {

    private final TaskService taskService;

    public TaskInfoController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/{id}")
    public String showTaskInfo(@PathVariable Long id, @RequestParam(required = false) String error,
                               Model model, HttpServletRequest request){

        if(error != null && !error.isEmpty()){
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }

        Task task;
        String username = request.getRemoteUser();
        String textAndType;

        try{
            task = this.taskService.findById(id);
            textAndType = this.taskService.filter(task, username);
        }catch (TaskNotFoundException | UserNotFoundException exception){
            return "redirect:/tasks?error="+exception.getMessage();
        }

        List<User> assignedUsers = task.getAssignees().stream().distinct().collect(Collectors.toList());

        model.addAttribute("selectedTask", task);
        model.addAttribute("textAndType", textAndType);
        model.addAttribute("assignedUsers", assignedUsers);

        return "taskInfo";
    }

    @PostMapping("/{id}/joinTask")
    public String addAssignee(@PathVariable Long id, HttpServletRequest request){

        String username = request.getRemoteUser();

        try {
            this.taskService.addAssignee(id, username);
        } catch (UserAlreadyAssignedException | TaskNotFoundException | UserNotFoundException exception){
            String url = "/taskInfo/"+id+"?error="+exception.getMessage();

            return "redirect:"+url;
        }

        return "redirect:/workOnTask/"+id;
    }

}