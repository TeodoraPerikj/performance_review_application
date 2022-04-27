package mk.ukim.finki.performance_review.web.controller;

import mk.ukim.finki.performance_review.model.User;
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

    public CheckPerformanceController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showPerformancePage1(@RequestParam(required = false) String error, Model model){

        if(error != null && !error.isEmpty()){
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }

        List<User> listUsers = this.userService.listAll();

        List<String> listTypes = new ArrayList<>();
        listTypes.add("Weekly");
        listTypes.add("Monthly");
        listTypes.add("Yearly");

        model.addAttribute("listUsers", listUsers);
        model.addAttribute("listTypes", listTypes);

        return "showPerformance";
    }

}
