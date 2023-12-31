package hexlet.code.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.dto.LabelDTO;
import hexlet.code.model.Label;
import hexlet.code.model.User;
import hexlet.code.repository.LabelRepository;
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
public class LabelControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserUtil userUtil;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LabelRepository labelRepository;
    @Autowired
    private ObjectMapper om;

    @Autowired
    private Faker faker;

    private User testUser;

    private Label testLabel;

    private SecurityMockMvcRequestPostProcessors.JwtRequestPostProcessor token;

    @BeforeEach
    public void setUp() {
        testUser = userUtil.makeUser();
        userRepository.save(testUser);

        testLabel = new Label();
        testLabel.setName(faker.name().title());
        labelRepository.save(testLabel);

        token = jwt().jwt(builder -> builder.subject(testUser.getEmail()));
    }

    @Test
    public void testShowLabels() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(
                get("/api/labels")
                        .with(token)
        ).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getContentAsString()).contains(testLabel.getName());

    }

    @Test
    public void testShowLabel() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(
                get("/api/labels/" + testLabel.getId())
                        .with(token)
        ).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getContentAsString())
                .contains(testLabel.getName());
    }

    @Test
    public void testUpdateLabel() throws Exception {
        LabelDTO labelData = new LabelDTO();
        labelData.setName(JsonNullable.of(faker.name().title()));
        MockHttpServletResponse response = mockMvc.perform(
                put("/api/labels/" + testLabel.getId())
                        .with(token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(labelData))
        ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(200);
        testLabel = labelRepository.findById(testLabel.getId()).orElse(null);
        assertThat(response.getContentAsString()).contains(testLabel.getName());
    }

    @Test
    public void testDeleteLabel() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(
                delete("/api/labels/" + testLabel.getId())
                        .with(token)
        ).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(204);

        response = mockMvc.perform(
                get("/api/labels")
                        .with(token)
        ).andReturn().getResponse();
        assertThat(response.getContentAsString()).doesNotContain(testLabel.getName());
    }

    @Test
    public void testCreateLabel() throws Exception {
        LabelDTO label = new LabelDTO();
        label.setName(JsonNullable.of(faker.name().title()));
        MockHttpServletResponse response = mockMvc.perform(
                post("/api/labels")
                        .with(token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(label))
        ).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(201);
        assertThat(response.getContentAsString()).contains(label.getName().get());
    }
}
