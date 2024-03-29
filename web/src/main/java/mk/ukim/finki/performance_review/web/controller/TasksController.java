package mk.ukim.finki.performance_review.web.controller;

import mk.ukim.finki.performance_review.model.Task;
import mk.ukim.finki.performance_review.model.User;
import mk.ukim.finki.performance_review.model.exceptions.*;
import mk.ukim.finki.performance_review.service.TaskService;
import mk.ukim.finki.performance_review.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class TasksController {

    private final TaskService taskService;
    private final UserService userService;

    public TasksController(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }

    @GetMapping(value = {"/","/tasks"})
    public String showTasks1(@RequestParam(required = false) String error, Model model){

        if(error != null && !error.isEmpty()){
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }

        List<Task> allTasks = this.taskService.listAll();

        model.addAttribute("allTasks", allTasks);

        return "showTasks";
    }

    @GetMapping("/tasks/add-task")
    public String getAddTaskPage1(Model model){

        List<User> users = this.userService.listAll();

        model.addAttribute("users", users);

        return "add_task";
    }

    @GetMapping("/tasks/edit-task/{id}")
    public String getEditTaskPage1(@PathVariable Long id, Model model){

        Task task = null;

        try{
            if(this.taskService.findById(id).isPresent())
                task = this.taskService.findById(id).get();
        }catch (TaskNotFoundException exception){
            return "redirect:/tasks?error="+exception.getMessage();
        }

        model.addAttribute("task", task);
        model.addAttribute("users", this.userService.listAll());

        return "add_task";
    }

    @PostMapping("/tasks/add")
    public String addNewTask1(@RequestParam String title, @RequestParam String description,
                             @RequestParam String startDate, @RequestParam String dueDate,
                             @RequestParam Integer days, @RequestParam List<String> assignees,
                             HttpServletRequest request){

        List<User> assignedUsers = new ArrayList<>();

        for(String username : assignees){

            User findUser = null;

            try{
                if(this.userService.findByUsername(username).isPresent())
                    findUser = this.userService.findByUsername(username).get();
            }catch (UserNotFoundException exception){
                return "redirect:/tasks?error="+exception.getMessage();
            }

            assignedUsers.add(findUser);
        }

        try{
            this.taskService.create(title, description, startDate,
                    dueDate, days, request.getRemoteUser(), assignedUsers);
        }catch (LocalDateTimeException | InvalidArgumentsException | EstimationDaysOutOfRangeException
                | EstimationDaysGreaterThanDaysToWorkOnTaskException exception){
            return "redirect:/tasks?error="+exception.getMessage();
        }

        return "redirect:/tasks";
    }

    @PostMapping("/tasks/add/{id}")
    public String editTask1(@PathVariable Long id, @RequestParam String title, @RequestParam String description,
                           @RequestParam String startDate, @RequestParam String dueDate, @RequestParam Integer days,
                           @RequestParam(required = false) List<String> assignees){

        List<User> users = new ArrayList<>();

        if(assignees != null) {
            for (String username : assignees) {
                User findUser = null;

                try {
                    if(this.userService.findByUsername(username).isPresent())
                        findUser = this.userService.findByUsername(username).get();
                } catch (UserNotFoundException exception) {
                    return "redirect:/tasks?error=" + exception.getMessage();
                }
                users.add(findUser);
            }
        }
        else{
            Task task = null;
            try{
                if(this.taskService.findById(id).isPresent())
                    task = this.taskService.findById(id).get();
            }catch (TaskNotFoundException exception){
                return "redirect:/tasks?error="+exception.getMessage();
            }

            users = task.getAssignees().stream().distinct().collect(Collectors.toList());
        }

        try {
            this.taskService.edit(id, title, description, startDate, dueDate, days, users);
        }catch (LocalDateTimeException | InvalidArgumentsException | EstimationDaysOutOfRangeException
                | EstimationDaysGreaterThanDaysToWorkOnTaskException exception){
            return "redirect:/tasks?error="+exception.getMessage();
        }

        return "redirect:/tasks";
    }

    @PostMapping("/tasks/delete/{id}")
    public String deleteTask1(@PathVariable Long id){

        try {
            this.taskService.delete(id);
        }catch (TaskNotFoundException exception){
            return "redirect:/tasks?error="+exception.getMessage();
        }
        return "redirect:/tasks";
    }
}
