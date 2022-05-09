package mk.ukim.finki.performance_review.model.dto;

import lombok.Data;
import mk.ukim.finki.performance_review.model.User;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class TaskDto {

    String title;
    String description;
    String startDate;
    String dueDate;
    String estimationDays;
    String assignees;
    String creator;

    public TaskDto(String title, String description, String startDate, String dueDate,
                   String estimationDays, String assignees, String creator){
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.estimationDays = estimationDays;
        this.assignees = assignees;
        this.creator = creator;
    }

}
