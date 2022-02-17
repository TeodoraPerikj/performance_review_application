package mk.ukim.finki.performance_review.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    private Task task;

    private String comment;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dateTime;

    public Comment(){}

    public Comment(User user, Task task, String comment, LocalDateTime dateTime){

        this.user = user;
        this.task = task;
        this.comment = comment;
        this.dateTime = dateTime;

    }

}
