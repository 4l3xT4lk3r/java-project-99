package hexlet.code.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.openapitools.jackson.nullable.JsonNullable;

import java.time.LocalDate;

@Setter
@Getter
public class LabelDTO {
    private JsonNullable<Long> id;
    private JsonNullable<String> name;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private JsonNullable<LocalDate> createdAt;
}
