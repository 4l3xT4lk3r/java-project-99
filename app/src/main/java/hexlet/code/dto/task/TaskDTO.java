package hexlet.code.dto.task;


import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class TaskDTO {

    private Long id;

    private int index;

    private LocalDate createdAt;

    @JsonAlias("assignee_id")
    private Long assigneeId;

    private String title;

    private String content;

    private String status;

}
