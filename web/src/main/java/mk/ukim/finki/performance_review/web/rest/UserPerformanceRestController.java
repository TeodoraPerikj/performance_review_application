package mk.ukim.finki.performance_review.web.rest;

import mk.ukim.finki.performance_review.model.dto.UserPerformanceDto;
import mk.ukim.finki.performance_review.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/showUserPerformance")
public class UserPerformanceRestController {

    private final UserService userService;

    public UserPerformanceRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<UserPerformanceDto> showUserPerformance2(@RequestParam String chosenUsername,
                                                                  @RequestParam String type, @RequestParam(required = false) String dateFrom,
                                                                  @RequestParam(required = false) String dateTo){

        return this.userService.showUserPerformance(chosenUsername, type, dateFrom, dateTo)
                .map(userPerformanceDto -> ResponseEntity.ok().body(userPerformanceDto))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }
}
