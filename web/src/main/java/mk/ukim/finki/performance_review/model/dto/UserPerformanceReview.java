package mk.ukim.finki.performance_review.model.dto;

import lombok.Data;

@Data
public class UserPerformanceReview {

    String chosenUsername;
    String chosenType;
    String dateFrom;
    String dateTo;

    public UserPerformanceReview(String chosenUsername, String chosenType, String dateFrom, String dateTo){
        this.chosenUsername = chosenUsername;
        this.chosenType = chosenType;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }
}
