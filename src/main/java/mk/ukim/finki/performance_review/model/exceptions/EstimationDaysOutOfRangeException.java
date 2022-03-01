package mk.ukim.finki.performance_review.model.exceptions;

public class EstimationDaysOutOfRangeException extends RuntimeException{

    public EstimationDaysOutOfRangeException(Integer value){
        super(String.format("Value %d is out of range for the estimation days",value));
    }

}
