package hexlet.code.dto;


import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.openapitools.jackson.nullable.JsonNullable;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class TaskDTO {

    private JsonNullable<Long> id;

    private JsonNullable<Integer> index;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private JsonNullable<LocalDate> createdAt;

    @JsonAlias("assignee_id")
    private JsonNullable<Long> assigneeId;

    private JsonNullable<String> title;

    @JsonInclude
    private JsonNullable<String> content;

    private JsonNullable<String> status;

    private List<JsonNullable<Long>> taskLabelIds;

}
