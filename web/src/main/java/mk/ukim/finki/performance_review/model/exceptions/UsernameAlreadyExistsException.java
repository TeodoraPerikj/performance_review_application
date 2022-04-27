package mk.ukim.finki.performance_review.model.exceptions;

public class UsernameAlreadyExistsException extends RuntimeException {

    public UsernameAlreadyExistsException(String username) {
        super(String.format("User with username %s already exists!",username));
    }
}
