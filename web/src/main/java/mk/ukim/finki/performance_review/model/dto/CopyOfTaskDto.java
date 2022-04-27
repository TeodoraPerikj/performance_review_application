package mk.ukim.finki.performance_review.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import mk.ukim.finki.performance_review.model.Comment;
import mk.ukim.finki.performance_review.model.User;
import mk.ukim.finki.performance_review.model.enumerations.TaskStatus;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class CopyOfTaskDto {

    private String title;

    private String description;

    private LocalDateTime startDate;

    private LocalDateTime dueDate;

    private Integer estimationDays;

    private List<String> assignees;

    public CopyOfTaskDto(String title, String description, LocalDateTime startDate, LocalDateTime dueDate,
                Integer estimationDays, List<String> assignees){

        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.estimationDays = estimationDays;
        this.assignees = assignees;

    }
}
