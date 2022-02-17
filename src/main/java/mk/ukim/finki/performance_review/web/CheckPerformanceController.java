package mk.ukim.finki.performance_review.web;

import mk.ukim.finki.performance_review.model.Task;
import mk.ukim.finki.performance_review.model.User;
import mk.ukim.finki.performance_review.service.TaskService;
import mk.ukim.finki.performance_review.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/checkPerformance")
public class CheckPerformanceController {

    private final UserService userService;
    private final TaskService taskService;

    public CheckPerformanceController(UserService userService, TaskService taskService) {
        this.userService = userService;
        this.taskService = taskService;
    }

    @GetMapping
    public String showPerformancePage(@RequestParam(required = false) String error, Model model){

        if(error != null && !error.isEmpty()){
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }

        List<User> listUsers = this.userService.listAll();
        List<Task> listTasks = this.taskService.listAll();

        List<String> listTypes = new ArrayList<>();
        listTypes.add("Weekly");
        listTypes.add("Monthly");
        listTypes.add("Yearly");

        model.addAttribute("listUsers", listUsers);
        model.addAttribute("listTasks", listTasks);
        model.addAttribute("listTypes", listTypes);

        return "showPerformance";
    }

}
