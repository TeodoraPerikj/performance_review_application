package mk.ukim.finki.performance_review.model.dto;

import lombok.Data;
import mk.ukim.finki.performance_review.model.Task;
import mk.ukim.finki.performance_review.model.User;

import java.util.List;

@Data
public class TaskUsersDto {

    private Task task;

    private List<User> users;

    public TaskUsersDto(Task task, List<User> users){
        this.task = task;
        this.users = users;
    }
}
