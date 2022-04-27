package mk.ukim.finki.performance_review.model.dto;

import lombok.Data;
import mk.ukim.finki.performance_review.model.Task;

@Data
public class CommentsDto {

    String comment;
    String title;

    public CommentsDto(String comment, String title){
        this.comment = comment;
        this.title = title;
    }

}
