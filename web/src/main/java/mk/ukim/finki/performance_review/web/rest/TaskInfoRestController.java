package mk.ukim.finki.performance_review.web.rest;

import mk.ukim.finki.performance_review.model.Task;
import mk.ukim.finki.performance_review.model.dto.TaskInfoDto;
import mk.ukim.finki.performance_review.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/taskInfo_2") //TODO CHANGE THE URL
public class TaskInfoRestController {

    private final TaskService taskService;

    public TaskInfoRestController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskInfoDto> showTaskInfo2(@PathVariable Long id, HttpServletRequest request){

        return this.taskService.showTaskInfo(id, request.getRemoteUser())
                .map(taskInfoDto -> ResponseEntity.ok().body(taskInfoDto))
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    @PostMapping("/{id}/joinTask")
    public ResponseEntity<Task> addAssignee2(@PathVariable Long id, HttpServletRequest request){
        return this.taskService.addAssignee(id, request.getRemoteUser())
                .map(task -> ResponseEntity.ok().body(task))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
