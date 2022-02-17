package mk.ukim.finki.performance_review.web;

import mk.ukim.finki.performance_review.model.Task;
import mk.ukim.finki.performance_review.model.enumerations.TaskStatus;
import mk.ukim.finki.performance_review.model.User;
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
@RequestMapping("/myTasks")
public class UserTasksController {

    private final TaskService taskService;
    private final UserService userService;

    public UserTasksController(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }

    @GetMapping
    public String showMyTasks(@RequestParam(required = false) String error,
                              HttpServletRequest request, Model model){

        if(error != null && !error.isEmpty()){
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }

        List<Task> myToDoTasks;
        List<Task> myInProgressTasks;
        List<Task> myDoneTasks;
        List<Task> myCanceledTasks;
        List<Task> ownedTasks;
        String username = request.getRemoteUser();

        try {

            myToDoTasks = this.userService.listTasksByStatus(username, TaskStatus.TODO);
            myInProgressTasks = this.userService.listTasksByStatus(username, TaskStatus.InProgress);
            myDoneTasks = this.userService.listTasksByStatus(username, TaskStatus.Done);
            myCanceledTasks = this.userService.listTasksByStatus(username, TaskStatus.Canceled);
            ownedTasks = this.taskService.findByCreator(username);

        }catch (InvalidArgumentsException exception){
            return "redirect:/myTasks?error="+exception.getMessage();
        }

        List<Integer> numberOfTasks = this.userService.findNumberOfTasks(username);

        model.addAttribute("myToDoTasks", myToDoTasks);
        model.addAttribute("myInProgressTasks", myInProgressTasks);
        model.addAttribute("myDoneTasks", myDoneTasks);
        model.addAttribute("myCanceledTasks", myCanceledTasks);
        model.addAttribute("ownedTasks", ownedTasks);
        model.addAttribute("TODOTasks", numberOfTasks.get(0));
        model.addAttribute("openTasks", numberOfTasks.get(1));
        model.addAttribute("doneTasks", numberOfTasks.get(2));
        model.addAttribute("canceledTasks", numberOfTasks.get(3));

        return "showMyTasks";
    }

    @GetMapping("/add-task")
    public String getAddTaskPage(Model model){

        List<User> users = this.userService.listAll();

        model.addAttribute("users", users);

        return "add_task";
    }

    @GetMapping("/edit-task/{id}")
    public String getEditTaskPage(@PathVariable Long id, Model model){

        Task task;

        try{
            task = this.taskService.findById(id);
        }catch (TaskNotFoundException exception){
            return "redirect:/myTasks?error="+exception.getMessage();
        }

        model.addAttribute("myTask", task);
        model.addAttribute("users", this.userService.listAll());

        return "add_task";
    }

    @PostMapping("/add")
    public String addNewTask(@RequestParam String title, @RequestParam String description, @RequestParam String startDate,
                             @RequestParam String dueDate, @RequestParam Integer days,
                             @RequestParam List<String> assignees, HttpServletRequest request){

        List<User> assignedUsers;
        assignedUsers= new ArrayList<>();

        for(String username : assignees){

            User findUser;

            try{
                findUser = this.userService.findByUsername(username);
            }catch (UserNotFoundException exception){
                return "redirect:/myTasks?error="+exception.getMessage();
            }

            assignedUsers.add(findUser);
        }

        try{
            this.taskService.create(title, description, startDate,
                    dueDate, days, request.getRemoteUser(), assignedUsers);
        }catch (LocalDateTimeException | InvalidArgumentsException exception){
            return "redirect:/myTasks?error="+exception.getMessage();
        }


        return "redirect:/myTasks";
    }

    @PostMapping("/add/{id}")
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
                    return "redirect:/myTasks?error=" + exception.getMessage();
                }

                users.add(findUser);
            }
        }
        else{
            Task task;
            try{
                task = this.taskService.findById(id);
            }catch (TaskNotFoundException exception){
                return "redirect:/myTasks?error="+exception.getMessage();
            }
            users = task.getAssignees();
        }

        try {
            this.taskService.edit(id, title, description, startDate, dueDate, days, users);
        }catch (LocalDateTimeException | InvalidArgumentsException exception){
            return "redirect:/myTasks?error="+exception.getMessage();
        }

        return "redirect:/myTasks";
    }

    @PostMapping("/delete/{id}")
    public String deleteTask(@PathVariable Long id){

        try {
            this.taskService.delete(id);
        }catch (TaskNotFoundException exception){
            return "redirect:/myTasks?error="+exception.getMessage();
        }
        return "redirect:/myTasks";
    }
}