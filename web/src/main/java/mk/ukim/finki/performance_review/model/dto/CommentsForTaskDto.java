package mk.ukim.finki.performance_review.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentsForTaskDto {

    Long id;

    String comment;

    String username;

    LocalDateTime dateTime;

    public CommentsForTaskDto(Long id, String comment, String username, LocalDateTime dateTime){
        this.id = id;
        this.comment = comment;
        this.username = username;
        this.dateTime = dateTime;
    }
}
