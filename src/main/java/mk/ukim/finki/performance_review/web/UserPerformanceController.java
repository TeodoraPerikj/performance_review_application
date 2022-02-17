package mk.ukim.finki.performance_review.web;

import mk.ukim.finki.performance_review.model.User;
import mk.ukim.finki.performance_review.model.exceptions.UserNotFoundException;
import mk.ukim.finki.performance_review.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/showUserPerformance")
public class UserPerformanceController {

    private final UserService userService;

    public UserPerformanceController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showUserPerformance(@RequestParam String chosenUsername,
                                      @RequestParam String type, @RequestParam(required = false) String dateFrom,
                                      @RequestParam(required = false) String dateTo, Model model){

        User user;

        try {
            user = this.userService.findByUsername(chosenUsername);
        }catch (UserNotFoundException exception){
            return "redirect:/checkPerformance?error="+exception.getMessage();
        }

        List<Integer> numberOfTasks = null;
        Double calculatedPerformance = Double.valueOf(0);

        if(type.equals("Weekly")){
            numberOfTasks = this.userService.filterByDate(user.getUsername(), dateFrom, dateTo);
        }else {
            numberOfTasks = this.userService.findNumberOfTasksByType(user.getUsername(), type);
        }

        Integer numberOfTasksAssigned = numberOfTasks.get(0)+numberOfTasks.get(1)
                +numberOfTasks.get(2)+numberOfTasks.get(3);

        Integer numberOfDoneTasks = numberOfTasks.get(2);

        calculatedPerformance = Double.valueOf(numberOfDoneTasks)/Double.valueOf(numberOfTasksAssigned);
        calculatedPerformance*=100;

        String rate;

        if(calculatedPerformance<50)
            rate = "Underrated";
        else if(calculatedPerformance > 50)
            rate = "Overrated";
        else if(calculatedPerformance.equals(50.0)) rate = "Fine";
        else rate = "Do not have tasks to see performance";

        model.addAttribute("userTODOTasks", numberOfTasks.get(0));
        model.addAttribute("userOpenTasks", numberOfTasks.get(1));
        model.addAttribute("userDoneTasks", numberOfTasks.get(2));
        model.addAttribute("userCanceledTasks", numberOfTasks.get(3));
        model.addAttribute("type", type);
        model.addAttribute("calculatedPerformance", calculatedPerformance);
        model.addAttribute("rate", rate);

        model.addAttribute("selectedUser", user);

        return "showUserPerformance";
    }

}
