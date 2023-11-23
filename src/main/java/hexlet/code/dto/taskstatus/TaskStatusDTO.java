package hexlet.code.dto.taskstatus;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskStatusDTO {

    private Long id;

    private String name;

    private String slug;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private String createdAt;
}
