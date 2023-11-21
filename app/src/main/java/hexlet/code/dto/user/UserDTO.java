package hexlet.code.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class UserDTO {

    private Long id;

    private String email;

    private String lastName;

    private String firstName;

    @JsonFormat(pattern="yyyy-MM-dd")
    private String createdAt;

}
