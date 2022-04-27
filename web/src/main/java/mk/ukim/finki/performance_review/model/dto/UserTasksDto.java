package mk.ukim.finki.performance_review.model.dto;

import lombok.Data;
import mk.ukim.finki.performance_review.model.Task;

import java.util.List;

@Data
public class UserTasksDto {

    private List<Task> myToDoTasks;
    private List<Task> myInProgressTasks;
    private List<Task> myDoneTasks;
    private List<Task> myCanceledTasks;
    private List<Task> ownedTasks;
    private String username;
    private List<Integer> numberOfTasks;

    public UserTasksDto(List<Task> myToDoTasks, List<Task> myInProgressTasks, List<Task> myDoneTasks,
                        List<Task> myCanceledTasks, List<Task> ownedTasks, String username, List<Integer> numberOfTasks){
        this.myToDoTasks = myToDoTasks;
        this.myInProgressTasks = myInProgressTasks;
        this.myDoneTasks = myDoneTasks;
        this.myCanceledTasks = myCanceledTasks;
        this.ownedTasks = ownedTasks;
        this.username = username;
        this.numberOfTasks = numberOfTasks;
    }
}
