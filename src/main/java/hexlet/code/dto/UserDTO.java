package hexlet.code.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.openapitools.jackson.nullable.JsonNullable;

@Getter
@Setter
public class UserDTO {

    private JsonNullable<Long> id;

    private JsonNullable<String> email;

    private JsonNullable<String> lastName;

    private JsonNullable<String> firstName;

    private JsonNullable<String> password;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private JsonNullable<String> createdAt;

}
