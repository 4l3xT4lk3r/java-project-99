package hexlet.code.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.model.TaskStatus;
import hexlet.code.model.User;
import hexlet.code.repository.TaskStatusRepository;
import hexlet.code.repository.UserRepository;
import hexlet.code.utils.UserUtil;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
public class TaskStatusControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserUtil userUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskStatusRepository taskStatusRepository;
    @Autowired
    private ObjectMapper om;

    @Autowired
    private Faker faker;

    private User testUser;

    private String plainPassword;

    private SecurityMockMvcRequestPostProcessors.JwtRequestPostProcessor token;

    @BeforeEach
    public void setUp() {
        User testUser = userUtil.makeUser();
        plainPassword = userUtil.getPlainPassword();
        userRepository.save(testUser);
        token = jwt().jwt(builder -> builder.subject(testUser.getEmail()));
    }

    @Test
    public void testShowTaskStatuses() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(
                get("/api/task_statuses")
                        .with(token)
        ).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getContentAsString())
                .contains("draft", "to_review", "to_be_fixed", "to_publish");
    }

    @Test
    public void testShowTaskStatus() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(
                get("/api/task_statuses/1")
                        .with(token)
        ).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getContentAsString())
                .contains("draft");
    }

    @Test
    public void testUpdateTaskStatus() throws Exception {
        TaskStatus taskStatus = new TaskStatus();
        taskStatus.setName(faker.name().name());
        MockHttpServletResponse response = mockMvc.perform(
                put("/api/task_statuses/1")
                        .with(token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(taskStatus))
        ).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getContentAsString()).contains(taskStatus.getName());
    }

    @Test
    public void testDeleteTaskStatus() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(
                delete("/api/task_statuses/5")
                        .with(token)
        ).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(204);

        response = mockMvc.perform(
                get("/api/task_statuses")
                        .with(token)
        ).andReturn().getResponse();
        assertThat(response.getContentAsString()).doesNotContain("published");
    }

    @Test
    public void testCreateTaskStatus() throws Exception {
        TaskStatus taskStatus = new TaskStatus();
        taskStatus.setName(faker.name().name());
        taskStatus.setSlug(faker.name().name());
        MockHttpServletResponse response = mockMvc.perform(
                post("/api/task_statuses")
                        .with(token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(taskStatus))
        ).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(201);
        assertThat(response.getContentAsString()).contains(taskStatus.getName());
    }

}
