package mk.ukim.finki.performance_review.web.rest;

import mk.ukim.finki.performance_review.model.dto.AllUsersAndTasksDto;
import mk.ukim.finki.performance_review.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/help")
public class HelpRestController {

    private final TaskService taskService;

    public HelpRestController(TaskService taskService) {
        this.taskService = taskService;
    }


    @GetMapping
    public ResponseEntity<AllUsersAndTasksDto> getHelpPage2(Model model){

        return this.taskService.getHelpPage()
                .map(all -> ResponseEntity.ok().body(all))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }
}
