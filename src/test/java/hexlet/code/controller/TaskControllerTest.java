package hexlet.code.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.dto.TaskDTO;
import hexlet.code.model.Task;
import hexlet.code.model.TaskStatus;
import hexlet.code.model.User;
import hexlet.code.repository.TaskRepository;
import hexlet.code.repository.TaskStatusRepository;
import hexlet.code.repository.UserRepository;
import hexlet.code.utils.UserUtil;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openapitools.jackson.nullable.JsonNullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
public class TaskControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserUtil userUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskStatusRepository taskStatusRepository;
    @Autowired
    private ObjectMapper om;

    @Autowired
    private Faker faker;

    private User testUser;

    private Task testTask;

    private String plainPassword;

    private SecurityMockMvcRequestPostProcessors.JwtRequestPostProcessor token;

    @BeforeEach
    public void setUp() {
        User testUser = userUtil.makeUser();
        plainPassword = userUtil.getPlainPassword();
        userRepository.save(testUser);

        TaskStatus taskStatus = taskStatusRepository.findByName("draft").orElse(null);

        testTask = new Task();
        testTask.setName(faker.name().name());
        testTask.setTaskStatus(taskStatus);
        taskRepository.save(testTask);

        token = jwt().jwt(builder -> builder.subject(testUser.getEmail()));
    }

    @Test
    public void testShowTasks() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(
                get("/api/tasks")
                        .with(token)
        ).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getContentAsString()).contains(testTask.getName());

    }

    @Test
    public void testShowTask() throws Exception {
        System.out.println(testTask.getName());
        MockHttpServletResponse response = mockMvc.perform(
                get("/api/tasks/" + testTask.getId())
                        .with(token)
        ).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getContentAsString())
                .contains(testTask.getName());
    }

    @Test
    public void testUpdateTask() throws Exception {
        TaskDTO task = new TaskDTO();
        task.setTitle(JsonNullable.of(faker.name().name()));
        task.setId(JsonNullable.of(3L));

        MockHttpServletResponse response = mockMvc.perform(
                put("/api/tasks/" + testTask.getId())
                        .with(token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(task))
        ).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getContentAsString()).contains(task.getTitle().get());
    }

    @Test
    public void testDeleteTask() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(
                delete("/api/tasks/" + testTask.getId())
                        .with(token)
        ).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(204);

        response = mockMvc.perform(
                get("/api/tasks")
                        .with(token)
        ).andReturn().getResponse();
        assertThat(response.getContentAsString()).doesNotContain(testTask.getName());
    }

    @Test
    public void testCreateTask() throws Exception {
        TaskDTO task = new TaskDTO();
        task.setTitle(JsonNullable.of(faker.name().title()));
        task.setStatus(JsonNullable.of("draft"));
        MockHttpServletResponse response = mockMvc.perform(
                post("/api/tasks")
                        .with(token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(task))
        ).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(201);
        assertThat(response.getContentAsString()).contains(task.getTitle().get());
    }
}
