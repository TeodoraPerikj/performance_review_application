package mk.ukim.finki.performance_review.model.exceptions;

public class PasswordsDoNotMatchException extends RuntimeException {

    public PasswordsDoNotMatchException(){
        super("Passwords do not match!");
    }
}
