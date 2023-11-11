package hexlet.code.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserDTO {

    private Long id;

    private String email;

    private String lastName;

    private String firstName;

    private LocalDate createdAt;

}
