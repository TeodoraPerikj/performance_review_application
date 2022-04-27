package mk.ukim.finki.performance_review.web.rest.newRestControllers;

import mk.ukim.finki.performance_review.model.User;
import mk.ukim.finki.performance_review.model.dto.MyUserTasksDto;
import mk.ukim.finki.performance_review.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/findUser")
public class UserMyTasksRestController {

    private final UserService userService;

    public UserMyTasksRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public MyUserTasksDto findUser(@RequestParam String username) {

        User user = null;

        if (this.userService.findByUsername(username).isPresent())
            user = this.userService.findByUsername(username).get();


        MyUserTasksDto myUserTasksDto = this.userService.findTasksForTheUser(user.getUsername());

        return myUserTasksDto;
    }

}
