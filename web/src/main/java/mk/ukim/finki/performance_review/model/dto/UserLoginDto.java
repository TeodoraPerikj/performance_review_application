package mk.ukim.finki.performance_review.model.dto;

import lombok.Data;

@Data
public class UserLoginDto {

    String username;
    String password;

    public UserLoginDto(String username, String password){
        this.username = username;
        this.password = password;
    }
}
