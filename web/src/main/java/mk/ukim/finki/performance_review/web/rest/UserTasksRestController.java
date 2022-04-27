package mk.ukim.finki.performance_review.web.rest;

import mk.ukim.finki.performance_review.model.User;
import mk.ukim.finki.performance_review.model.dto.TaskUsersDto;
import mk.ukim.finki.performance_review.model.dto.UserTasksDto;
import mk.ukim.finki.performance_review.model.dto.UsersDto;
import mk.ukim.finki.performance_review.service.TaskService;
import mk.ukim.finki.performance_review.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/myTasks")
public class UserTasksRestController {

    private final TaskService taskService;
    private final UserService userService;

    public UserTasksRestController(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }

    @GetMapping("/showUsersDto")
    public List<UsersDto> showUsersDto(){
        return this.userService.showUsersDto();
    }

    @GetMapping
    public ResponseEntity<UserTasksDto> showUserTasks2(HttpServletRequest request){

        return this.userService.showUserTasks(request.getRemoteUser())
                .map(userTasksDto -> ResponseEntity.ok().body(userTasksDto))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @GetMapping("/add-task")
    public List<User> getAddTaskPage2(){

        return this.userService.listAll();
    }
//
//    @GetMapping("/edit-task/{id}")
//    public ResponseEntity<TaskUsersDto> getEditTaskPage2(@PathVariable Long id){
//
//        return this.taskService.getEditTask(id)
//                .map(taskUsersDto -> ResponseEntity.ok().body(taskUsersDto))
//                .orElseGet(() -> ResponseEntity.notFound().build());
//    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteTask2(@PathVariable Long id){

        this.taskService.delete(id);

        if(this.taskService.findById(id).isPresent())
            return ResponseEntity.badRequest().build();

        return ResponseEntity.ok().build();
    }

}
