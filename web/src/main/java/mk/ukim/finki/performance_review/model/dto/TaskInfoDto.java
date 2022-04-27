package mk.ukim.finki.performance_review.model.dto;

import lombok.Data;
import mk.ukim.finki.performance_review.model.Task;
import mk.ukim.finki.performance_review.model.User;

import java.util.List;

@Data
public class TaskInfoDto {

    private Task task;

    private String textAndType;

    private List<String> assignedUsers;

    public TaskInfoDto(Task task, String textAndType, List<String> assignedUsers){
        this.task = task;
        this.textAndType = textAndType;
        this.assignedUsers = assignedUsers;
    }
}
