package mk.ukim.finki.performance_review.model.exceptions;

public class TaskNotFoundException extends RuntimeException{

    public TaskNotFoundException(){
        super("Task not found!");
    }
}
