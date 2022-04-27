package mk.ukim.finki.performance_review.model.dto;

import lombok.Data;

@Data
public class UsersDto {

    String name;
    String surname;
    String username;


    public UsersDto(String name, String surname, String username) {
        this.name = name;
        this.surname = surname;
        this.username = username;
    }
}
