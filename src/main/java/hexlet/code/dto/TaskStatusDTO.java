package hexlet.code.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.openapitools.jackson.nullable.JsonNullable;

import java.time.LocalDate;

@Getter
@Setter
public class TaskStatusDTO {

    private JsonNullable<Long> id;

    private JsonNullable<String> name;

    private JsonNullable<String> slug;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private JsonNullable<LocalDate> createdAt;
}
