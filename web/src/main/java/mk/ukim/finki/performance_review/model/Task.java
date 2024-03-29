package mk.ukim.finki.performance_review.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import mk.ukim.finki.performance_review.model.enumerations.TaskStatus;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String title;

    private String description;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dueDate;

    private Integer estimationDays;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    private List<User> assignees;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    private User creator;

    @JsonIgnore
    @OneToMany(mappedBy = "task", fetch = FetchType.EAGER)
    private List<Comment> comments;

    public Task(){}

    public Task(String title, String description, LocalDateTime startDate, LocalDateTime dueDate,
                Integer estimationDays, User creator, List<User> assignees){

        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.estimationDays = estimationDays;
        this.status = TaskStatus.TODO;
        this.assignees = assignees;
        this.creator = creator;
        this.comments = new ArrayList<>();

    }

    @JsonIgnore
    public List<Comment> getDistinctComments(){
        return this.comments.stream().distinct().collect(Collectors.toList());
    }

}
