package mk.ukim.finki.performance_review.web;

import mk.ukim.finki.performance_review.service.TaskService;
import mk.ukim.finki.performance_review.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/help")
public class HelpController {

    private final UserService userService;
    private final TaskService taskService;

    public HelpController(UserService userService, TaskService taskService) {
        this.userService = userService;
        this.taskService = taskService;
    }


    @GetMapping
    public String getHelpPage(Model model){

        model.addAttribute("appUsers", this.userService.listAll());
        model.addAttribute("allTasks", this.taskService.listAll());

        return "help";
    }

}
