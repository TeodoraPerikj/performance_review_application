package mk.ukim.finki.performance_review.model.dto;

import lombok.Data;
import mk.ukim.finki.performance_review.model.Task;

import java.util.List;

@Data
public class MyUserTasksDto {

    List<Task> myToDoTasks;
    List<Task> myInProgressTasks;
    List<Task> myDoneTasks;
    List<Task> myCanceledTasks;
    List<Task> ownedTasks;
    List<Integer> numberOfTasks;

    public MyUserTasksDto(List<Task> myToDoTasks, List<Task> myInProgressTasks, List<Task> myDoneTasks, List<Task> myCanceledTasks,
                          List<Task> ownedTasks, List<Integer> numberOfTasks){
        this.myToDoTasks = myToDoTasks;
        this.myInProgressTasks = myInProgressTasks;
        this.myDoneTasks = myDoneTasks;
        this.myCanceledTasks = myCanceledTasks;
        this.ownedTasks = ownedTasks;
        this.numberOfTasks = numberOfTasks;
    }

}
