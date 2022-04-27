package mk.ukim.finki.performance_review.web.rest;

import mk.ukim.finki.performance_review.model.User;
import mk.ukim.finki.performance_review.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/register")
public class RegisterRestController {

    private final UserService userService;

    public RegisterRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity getRegisterPage2(){
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<User> register2(@RequestParam String username,
                                          @RequestParam String password,
                                          @RequestParam String repeatedPassword,
                                          @RequestParam String name,
                                          @RequestParam String surname) {

        return this.userService.register(name, surname, username, password, repeatedPassword)
                .map(user -> ResponseEntity.ok().body(user))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }
}
