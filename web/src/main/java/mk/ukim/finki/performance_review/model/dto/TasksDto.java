package mk.ukim.finki.performance_review.model.dto;

import lombok.Data;

@Data
public class TasksDto {

    String title;
    String description;

    public TasksDto(String title, String description){
        this.title = title;
        this.description = description;
    }
}
