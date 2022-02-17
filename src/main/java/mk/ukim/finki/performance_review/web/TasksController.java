package mk.ukim.finki.performance_review.web;

import mk.ukim.finki.performance_review.model.Task;
import mk.ukim.finki.performance_review.model.User;
import mk.ukim.finki.performance_review.model.enumerations.TaskStatus;
import mk.ukim.finki.performance_review.model.exceptions.InvalidArgumentsException;
import mk.ukim.finki.performance_review.model.exceptions.LocalDateTimeException;
import mk.ukim.finki.performance_review.model.exceptions.TaskNotFoundException;
import mk.ukim.finki.performance_review.model.exceptions.UserNotFoundException;
import mk.ukim.finki.performance_review.service.TaskService;
import mk.ukim.finki.performance_review.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class TasksController {

    private final TaskService taskService;
    private final UserService userService;

    public TasksController(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }

    @GetMapping(value = {"/","/tasks"})
    public String showTasks(@RequestParam(required = false) String error, Model model){

        if(error != null && !error.isEmpty()){
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }

        List<Task> toDoTasks;
        List<Task> inProgressTasks;
        List<Task> doneTasks;
        List<Task> canceledTasks;

        try {

            toDoTasks = this.taskService.findByStatus(TaskStatus.TODO);
            inProgressTasks = this.taskService.findByStatus(TaskStatus.InProgress);
            doneTasks = this.taskService.findByStatus(TaskStatus.Done);
            canceledTasks = this.taskService.findByStatus(TaskStatus.Canceled);

        }catch (InvalidArgumentsException exception){
            return "redirect:/tasks?error="+exception.getMessage();
        }

        model.addAttribute("toDoTasks", toDoTasks);
        model.addAttribute("inProgressTasks", inProgressTasks);
        model.addAttribute("doneTasks", doneTasks);
        model.addAttribute("canceledTasks", canceledTasks);
        model.addAttribute("allTasks", this.taskService.listAll());

        return "showTasks";
    }

    @GetMapping("/tasks/add-task")
    public String getAddTaskPage(Model model){

        List<User> users = this.userService.listAll();

        model.addAttribute("users", users);

        return "add_task";
    }

    @GetMapping("/tasks/edit-task/{id}")
    public String getEditTaskPage(@PathVariable Long id, Model model){

        Task task = null;

        try{
            task = this.taskService.findById(id);
        }catch (TaskNotFoundException exception){
            return "redirect:/tasks?error="+exception.getMessage();
        }

        model.addAttribute("task", task);
        model.addAttribute("users", this.userService.listAll());

        return "add_task";
    }

    @PostMapping("/tasks/add")
    public String addNewTask(@RequestParam String title, @RequestParam String description,
                             @RequestParam String startDate, @RequestParam String dueDate,
                             @RequestParam Integer days, @RequestParam List<String> assignees,
                             HttpServletRequest request){

        List<User> assignedUsers = new ArrayList<>();

        for(String username : assignees){

            User findUser;

            try{
                findUser = this.userService.findByUsername(username);
            }catch (UserNotFoundException exception){
                return "redirect:/tasks?error="+exception.getMessage();
            }

            assignedUsers.add(findUser);
        }

        try{
            this.taskService.create(title, description, startDate,
                    dueDate, days, request.getRemoteUser(), assignedUsers);
        }catch (LocalDateTimeException | InvalidArgumentsException exception){
            return "redirect:/tasks?error="+exception.getMessage();
        }

        return "redirect:/tasks";
    }

    @PostMapping("/tasks/add/{id}")
    public String editTask(@PathVariable Long id, @RequestParam String title, @RequestParam String description,
                           @RequestParam String startDate, @RequestParam String dueDate, @RequestParam Integer days,
                           @RequestParam(required = false) List<String> assignees){

        List<User> users = new ArrayList<>();

        if(assignees != null) {
            for (String username : assignees) {
                User findUser;

                try {
                    findUser = this.userService.findByUsername(username);
                } catch (UserNotFoundException exception) {
                    return "redirect:/tasks?error=" + exception.getMessage();
                }
                users.add(findUser);
            }
        }
        else{
            Task task;
            try{
                task = this.taskService.findById(id);
            }catch (TaskNotFoundException exception){
                return "redirect:/tasks?error="+exception.getMessage();
            }

            users = task.getAssignees();
        }

        try {
            this.taskService.edit(id, title, description, startDate, dueDate, days, users);
        }catch (LocalDateTimeException | InvalidArgumentsException exception){
            return "redirect:/tasks?error="+exception.getMessage();
        }

        return "redirect:/tasks";
    }

    @PostMapping("/tasks/delete/{id}")
    public String deleteTask(@PathVariable Long id){

        try {
            this.taskService.delete(id);
        }catch (TaskNotFoundException exception){
            return "redirect:/tasks?error="+exception.getMessage();
        }
        return "redirect:/tasks";
    }
}
