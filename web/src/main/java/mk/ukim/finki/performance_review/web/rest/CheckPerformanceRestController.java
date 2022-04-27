package mk.ukim.finki.performance_review.web.rest;

import mk.ukim.finki.performance_review.model.User;
import mk.ukim.finki.performance_review.service.UserService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/checkPerformance")
public class CheckPerformanceRestController {

    private final UserService userService;

    public CheckPerformanceRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> showPerformancePage2(){

        return this.userService.listAll();
    }

}
