package mk.ukim.finki.performance_review.web.rest;

import mk.ukim.finki.performance_review.config.filters.JWTAuthenticationFilter;
import mk.ukim.finki.performance_review.model.User;
import mk.ukim.finki.performance_review.model.dto.UserLoginDto;
import mk.ukim.finki.performance_review.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/login")
public class LoginRestController {

    private final UserService userService;

    public LoginRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity getLoginPage2(){
        return ResponseEntity.ok().build();
    }
//
//    @PostMapping
//    public String doLogin(HttpServletRequest request,
//                          HttpServletResponse response) throws JsonProcessingException {
//        Authentication auth = this.filter.attemptAuthentication(request, response);
//        return this.filter.generateJwt(response, auth);
//
//    }


    @PostMapping
    public ResponseEntity<User> login2(@RequestBody UserLoginDto userLoginDto){

        String username = userLoginDto.getUsername();
        String password = userLoginDto.getPassword();

        Optional<User> user = this.userService.login(username, password);

//        user.ifPresent(value -> request.getSession().setAttribute("user", value));

        return user.map(user1 -> ResponseEntity.ok().body(user1))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

//    @PostMapping
//    public ResponseEntity<User> login2(HttpServletRequest request){
//        Optional<User> user = this.userService.login(request.getParameter("username"),
//                request.getParameter("password"));
//
//        user.ifPresent(value -> request.getSession().setAttribute("user", value));
//
//        return user.map(user1 -> ResponseEntity.ok().body(user1))
//                .orElseGet(() -> ResponseEntity.badRequest().build());
//    }

}
