package mk.ukim.finki.performance_review.model.dto;

import lombok.Data;
import mk.ukim.finki.performance_review.model.enumerations.TaskStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class EachTaskDto {

    private Long id;

    private String title;

    private String description;

    private LocalDateTime startDate;

    private LocalDateTime dueDate;

    private Integer estimationDays;

    private TaskStatus status;

    private List<String> assignees;

    private String creator;

    private List<CommentsForTaskDto> comments;

    public EachTaskDto(Long id, String title, String description, LocalDateTime startDate, LocalDateTime dueDate, Integer estimationDays,
                       TaskStatus taskStatus, List<String> assignees, String creator, List<CommentsForTaskDto> comments){

        this.id = id;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.estimationDays = estimationDays;
        this.status = taskStatus;
        this.assignees = assignees;
        this.creator = creator;
        this.comments = comments;
    }

}
