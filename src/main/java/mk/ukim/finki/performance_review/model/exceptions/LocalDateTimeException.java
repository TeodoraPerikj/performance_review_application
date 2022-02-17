package mk.ukim.finki.performance_review.model.exceptions;

public class LocalDateTimeException extends RuntimeException {
    public LocalDateTimeException(){
        super("Start date can not be after due date!");
    }
}
