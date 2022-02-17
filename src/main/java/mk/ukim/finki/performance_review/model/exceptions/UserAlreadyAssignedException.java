package mk.ukim.finki.performance_review.model.exceptions;

public class UserAlreadyAssignedException extends RuntimeException {
    public UserAlreadyAssignedException(){
        super("User is already assigned to the task!");
    }
}
