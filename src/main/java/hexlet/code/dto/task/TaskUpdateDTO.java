package hexlet.code.dto.task;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.openapitools.jackson.nullable.JsonNullable;

import java.util.List;

@Getter
@Setter
public class TaskUpdateDTO {
    private JsonNullable<Integer> index;

    @JsonAlias("assignee_id")
    private JsonNullable<Long> assigneeId;

    @NotBlank
    @Size(min = 1)
    private JsonNullable<String> title;

    private JsonNullable<String> content;

    @NotBlank
    private JsonNullable<String> status;

    private List<JsonNullable<Long>> taskLabelIds;
}
