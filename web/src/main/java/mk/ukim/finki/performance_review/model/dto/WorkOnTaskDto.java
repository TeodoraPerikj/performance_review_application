package mk.ukim.finki.performance_review.model.dto;

import lombok.Data;
import mk.ukim.finki.performance_review.model.Comment;
import mk.ukim.finki.performance_review.model.Task;
import mk.ukim.finki.performance_review.model.User;

import java.util.List;

@Data
public class WorkOnTaskDto {

    private Task task;

    private List<String> assignedUsers;

    private List<CommentsForTaskDto> comments;

    public WorkOnTaskDto(Task task, List<String> assignedUsers, List<CommentsForTaskDto> comments){
        this.task = task;
        this.assignedUsers = assignedUsers;
        this.comments = comments;
    }

}
