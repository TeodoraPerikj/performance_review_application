package mk.ukim.finki.performance_review.model.exceptions;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(){
        super("User not found!");
    }
}
