package mk.ukim.finki.performance_review.web.rest.newRestControllers;

import mk.ukim.finki.performance_review.model.dto.TaskInfoDto;
import mk.ukim.finki.performance_review.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/taskInfo")
public class NewTaskInfoRestController {

    private final TaskService taskService;

    public NewTaskInfoRestController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskInfoDto> showTaskInfo2(@PathVariable Long id, @RequestParam String username){

        return this.taskService.showTaskInfo(id, username)
                .map(taskInfoDto -> ResponseEntity.ok().body(taskInfoDto))
                .orElseGet(() -> ResponseEntity.notFound().build());

    }
}
