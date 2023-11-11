package hexlet.code.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import org.openapitools.jackson.nullable.JsonNullable;

public class UserUpdateDTO {

    @Email(message = "Wrong email format!")
    private JsonNullable<String> email;

    private JsonNullable<String> firstName;

    private JsonNullable<String> lastName;
    @Size(min=3,message = "Minimal password length is about 3 symbols!")
    private JsonNullable<String> password;
}
