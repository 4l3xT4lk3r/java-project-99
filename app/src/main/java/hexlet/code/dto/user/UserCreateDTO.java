package hexlet.code.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateDTO {

    @NotNull
    @Email(message = "Wrong email format!")
    private String email;

    private String firstName;

    private String lastName;

    @NotNull
    @Size(min = 3, message = "Minimal password length is about 3 symbols!")
    private String password;

}
