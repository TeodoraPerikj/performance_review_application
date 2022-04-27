package mk.ukim.finki.performance_review.model.dto;

import lombok.Data;
import mk.ukim.finki.performance_review.model.User;
import mk.ukim.finki.performance_review.model.enumerations.Role;

@Data
public class UserDetailsDto {
    private String username;
    private Role role;

    public static UserDetailsDto of(User user) {
        UserDetailsDto details = new UserDetailsDto();
        details.username = user.getUsername();

        if(user.getUsername().startsWith("u"))
            details.role = Role.ROLE_USER;
        details.role = Role.ROLE_ADMIN;
        return details;
    }
}
