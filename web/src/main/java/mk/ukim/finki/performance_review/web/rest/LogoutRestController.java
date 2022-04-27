package mk.ukim.finki.performance_review.web.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/logout")
public class LogoutRestController {

    @GetMapping
    public ResponseEntity logout2(HttpServletRequest request){
        request.getSession().invalidate();
        return ResponseEntity.ok().build();
    }

}
