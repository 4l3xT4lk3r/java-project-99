package hexlet.code.dto.task;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class TaskCreateDTO {

    private int index;

    @JsonAlias("assignee_id")
    private Long assigneeId;

    private String title;

    private String content;


    private String status;

    private List<Long> taskLabelIds;
}
