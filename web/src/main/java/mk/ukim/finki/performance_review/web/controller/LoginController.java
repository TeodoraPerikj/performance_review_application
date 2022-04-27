package mk.ukim.finki.performance_review.web.controller;

import mk.ukim.finki.performance_review.model.User;
import mk.ukim.finki.performance_review.model.exceptions.InvalidUserCredentialsException;
import mk.ukim.finki.performance_review.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/login")
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getLoginPage1(@RequestParam(required = false) String error, Model model) {
        if(error != null && !error.isEmpty()){
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }

        return "login";
    }

    @PostMapping
    public String login1(HttpServletRequest request) {
        User user = null;
        try{
            if(this.userService.login(request.getParameter("username"),
                    request.getParameter("password")).isPresent())
                user = this.userService.login(request.getParameter("username"),
                    request.getParameter("password")).get();
            request.getSession().setAttribute("user", user);
            return "redirect:/tasks";
        }
        catch (InvalidUserCredentialsException exception) {
            return "redirect:/login?error="+exception.getMessage();
        }
    }

}
