package hexlet.code.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.dto.AuthRequest;
import hexlet.code.dto.user.UserCreateDTO;
import hexlet.code.dto.user.UserUpdateDTO;
import hexlet.code.mapper.UserMapper;
import hexlet.code.model.User;
import hexlet.code.repository.UserRepository;
import hexlet.code.utils.UserUtil;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openapitools.jackson.nullable.JsonNullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.http.MediaType;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;


@SpringBootTest
@AutoConfigureMockMvc
public final class UsersControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private Faker faker;

    @Autowired
    private UserMapper mapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private ObjectMapper om;

    @Autowired
    private UserUtil userUtil;

    private User testUser;

    private SecurityMockMvcRequestPostProcessors.JwtRequestPostProcessor token;

    private String plainPassword;


    @BeforeEach
    public void setUp() {
        testUser = userUtil.makeUser();
        plainPassword = userUtil.getPlainPassword();
        userRepository.save(testUser);
        token = jwt().jwt(builder -> builder.subject(testUser.getEmail()));
    }
    @Test
    public void testRegistration() throws Exception {
        UserCreateDTO userCreateDTO = new UserCreateDTO();
        userCreateDTO.setEmail(faker.internet().emailAddress());
        userCreateDTO.setPassword(encoder.encode(faker.internet().password()));
        MockHttpServletResponse response = mockMvc.perform(
                post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(userCreateDTO))
        ).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(201);

        Optional<User> user = userRepository.findByEmail(userCreateDTO.getEmail());
        assertThat(user).isNotNull();
        assertThat(user.get().getEmail()).isEqualTo(userCreateDTO.getEmail());
    }


    @Test
    public void testAuth() throws Exception {
        AuthRequest authRequest = new AuthRequest();
        authRequest.setUsername(testUser.getUsername());
        authRequest.setPassword(plainPassword);
        MockHttpServletResponse response = mockMvc.perform(
                post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(authRequest))
        ).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(200);
    }

    @Test
    public void tesGetForeignPage() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(
                get("/api/users/1")
                        .with(jwt())
        ).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(403);
    }

    @Test
    public void testGetUsers() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(
                get("/api/users")
                        .with(jwt())
        ).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(200);
    }

    @Test
    public void testGetOwnPage() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(
                get("/api/users/" + testUser.getId())
                        .with(token)
        ).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getContentAsString()).contains(testUser.getEmail());
    }

    @Test
    public void testUpdateOwnPage() throws Exception {
        UserUpdateDTO userData = new UserUpdateDTO();
        userData.setFirstName(JsonNullable.of(faker.name().firstName()));
        userData.setLastName(JsonNullable.of(faker.name().lastName()));
        MockHttpServletResponse response = mockMvc.perform(
                put("/api/users/" + testUser.getId())
                        .with(token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(userData))
        ).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(200);

        Optional<User> user = userRepository.findByEmail(testUser.getEmail());
        assertThat(user).isNotNull();
        assertThat(user.get().getFirstName()).isEqualTo(userData.getFirstName().get());
    }

    @Test
    public void testDeleteOwnPage() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(
                delete("/api/users/" + testUser.getId())
                        .with(token)
        ).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(204);
    }
}
