package mk.ukim.finki.performance_review.model.dto;

import lombok.Data;
import mk.ukim.finki.performance_review.model.Task;
import mk.ukim.finki.performance_review.model.User;

import java.util.List;

@Data
public class AllUsersAndTasksDto {

    private List<User> users;

    private List<Task> tasks;

    public AllUsersAndTasksDto(List<User> users, List<Task> tasks){
        this.users = users;
        this.tasks = tasks;
    }
}
