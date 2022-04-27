package mk.ukim.finki.performance_review.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import mk.ukim.finki.performance_review.model.Task;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "app_user")
public class User {

    private String name;

    private String surname;

    @Id
    private String username;

    private String password;

    @JsonIgnore
    @ManyToMany(mappedBy = "assignees", fetch = FetchType.EAGER)
    private List<Task> taskAssigned;

    @JsonIgnore
    @OneToMany(mappedBy = "creator", fetch = FetchType.EAGER)
    private List<Task> taskOwned;

    public User() {
    }

    public User(String name, String surname, String username, String password) {

        this.name = name;
        this.surname = surname;
        this.username = username;
        this.password = password;
        this.taskAssigned = new ArrayList<>();
        this.taskOwned = new ArrayList<>();

    }

}
