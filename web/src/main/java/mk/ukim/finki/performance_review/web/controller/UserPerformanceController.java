package mk.ukim.finki.performance_review.web.controller;

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
    public String showUserPerformance1(@RequestParam String chosenUsername,
                                      @RequestParam String type, @RequestParam(required = false) String dateFrom,
                                      @RequestParam(required = false) String dateTo, Model model){

        User user = null;

        try {
            if(this.userService.findByUsername(chosenUsername).isPresent())
                user = this.userService.findByUsername(chosenUsername).get();
        }catch (UserNotFoundException exception){
            return "redirect:/checkPerformance?error="+exception.getMessage();
        }

        List<Integer> numberOfTasks;

        if(type.equals("Weekly")){
            if(dateFrom == null || dateFrom.isEmpty() || dateTo == null || dateTo.isEmpty())
                return "redirect:/checkPerformance?error=Date+from+or+date+to+is+empty!";
            numberOfTasks = this.userService.filterByDate(user.getUsername(), dateFrom, dateTo);
        }else {
            numberOfTasks = this.userService.findNumberOfTasksByType(user.getUsername(), type);
        }

        List<String> calculatedPerformanceAndRating = this.userService.calculatePerformanceAndRating(numberOfTasks);

        String calculatedPerformance = String.format("%.2f",Double.valueOf(calculatedPerformanceAndRating.get(1)));


        model.addAttribute("userTODOTasks", numberOfTasks.get(0));
        model.addAttribute("userOpenTasks", numberOfTasks.get(1));
        model.addAttribute("userDoneTasks", numberOfTasks.get(2));
        model.addAttribute("userCanceledTasks", numberOfTasks.get(3));
        model.addAttribute("type", type);
        model.addAttribute("rate", calculatedPerformanceAndRating.get(0));
        model.addAttribute("calculatedPerformance", Double.valueOf(calculatedPerformance));
        model.addAttribute("selectedUser", user);

        return "showUserPerformance";
    }

}
