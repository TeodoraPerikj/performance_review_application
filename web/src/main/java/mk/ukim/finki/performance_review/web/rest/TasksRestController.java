package mk.ukim.finki.performance_review.web.rest;

import mk.ukim.finki.performance_review.model.Task;
import mk.ukim.finki.performance_review.model.User;
import mk.ukim.finki.performance_review.model.dto.CopyOfTaskDto;
import mk.ukim.finki.performance_review.model.dto.EachTaskDto;
import mk.ukim.finki.performance_review.model.dto.TaskDto;
import mk.ukim.finki.performance_review.service.TaskService;
import mk.ukim.finki.performance_review.service.UserService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class TasksRestController {

    private final TaskService taskService;
    private final UserService userService;

    public TasksRestController(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }

    @GetMapping(value = {"/api", "/api/tasks"})
    public List<EachTaskDto> getAllTasks(){
        return this.taskService.findEachTask();
    }

//    @GetMapping(value = {"/api", "/api/tasks"})
//    public List<Task> getAllTasks(){
//        return this.taskService.listAll();
//    }

//    @GetMapping(value = {"/api/","/api/tasks"})
//    public List<TasksDto> showTasks2(){
//        return this.taskService.showTasksDto();
//    }

    @GetMapping("/api/tasks/pagination")
    public List<EachTaskDto> findTasksByPage(Pageable pageable){
        return this.taskService.findAllWithPagination(pageable).getContent();
    }


    @GetMapping("/api/tasks/getById/{id}")
    public Optional<CopyOfTaskDto> getById(@PathVariable Long id){
        Optional<CopyOfTaskDto> task = this.taskService.getEditTask(id);
        return task;
    }

    @GetMapping("/api/tasks/add-task")
    public List<User> getAddTaskPage2(Model model){

        return this.userService.listAll();
    }

//    @GetMapping("/api/tasks/edit-task/{id}")
//    public Task getEditTaskPage2(@PathVariable Long id){
//
//        Optional<Task> task = this.taskService.findById(id);
//
//       if(task.isPresent()) return task.get();
//
//       return null;
//    }

    @GetMapping("/api/tasks/edit-task/{id}")
    public ResponseEntity<Task> getEditTaskPage2(@PathVariable Long id){

        Optional<Task> task = this.taskService.findById(id);

        return task.map(taskById -> ResponseEntity.ok().body(taskById))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/api/tasks/add")
    public ResponseEntity<Task> addNewTask2(@RequestBody TaskDto taskDto){
        List<User> assignedUsers = this.userService.findAssignedUsers(taskDto.getAssignees());

        String title = taskDto.getTitle();
        String description = taskDto.getDescription();
        String startDate = taskDto.getStartDate();
        String dueDate = taskDto.getDueDate();
        Integer days = Integer.valueOf(taskDto.getEstimationDays());
        String username = taskDto.getCreator();

        return this.taskService.create(title, description, startDate, dueDate, days, username, assignedUsers)
                .map(task -> ResponseEntity.ok().body(task))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PutMapping("/api/tasks/add/{id}")
    public ResponseEntity<Task> editTask2(@PathVariable Long id, @RequestBody TaskDto taskDto){

        List<User> users = new ArrayList<>();

        if(taskDto.getAssignees() != null) {
            //TODO
           users = this.userService.findAssignedUsers(taskDto.getAssignees());
        }
        else{
            Task task = null;
            if(this.taskService.findById(id).isPresent())
                task = this.taskService.findById(id).get();

            users = task.getAssignees().stream().distinct().collect(Collectors.toList());
        }

        String title = taskDto.getTitle();
        String description = taskDto.getDescription();
        String startDate = taskDto.getStartDate();
        String dueDate = taskDto.getDueDate();
        Integer days = Integer.valueOf(taskDto.getEstimationDays());

        return this.taskService.edit(id, title, description, startDate, dueDate, days, users)
                .map(task -> ResponseEntity.ok().body(task))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/api/tasks/delete/{id}")
    public ResponseEntity deleteTask2(@PathVariable Long id){

        this.taskService.delete(id);

        if(this.taskService.findById(id).isPresent())
            return ResponseEntity.badRequest().build();

        return ResponseEntity.ok().build();
    }

}
