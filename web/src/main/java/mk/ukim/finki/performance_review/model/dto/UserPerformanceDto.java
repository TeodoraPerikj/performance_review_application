package mk.ukim.finki.performance_review.model.dto;

import lombok.Data;
import mk.ukim.finki.performance_review.model.User;

import java.util.List;

@Data
public class UserPerformanceDto {

    private User user;

    private List<Integer> numberOfTasks;

    private List<String> calculatedPerformanceAndRating;

    private Double calculatedPerformance;

    private String type;

    public UserPerformanceDto(User user, List<Integer> numberOfTasks, List<String> calculatedPerformanceAndRating,
                              Double calculatedPerformance, String type){

        this.user = user;
        this.numberOfTasks = numberOfTasks;
        this.calculatedPerformanceAndRating = calculatedPerformanceAndRating;
        this.calculatedPerformance = calculatedPerformance;
        this.type = type;
    }
}
