package hexlet.code.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.dto.AuthRequest;
import hexlet.code.mapper.UserMapper;
import hexlet.code.model.User;
import hexlet.code.repository.UserRepository;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.http.MediaType;

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
    private UserRepository repository;
    @Autowired
    private Faker faker;

    @Autowired
    private UserMapper mapper;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private ObjectMapper om;

    private User testUser;

    private SecurityMockMvcRequestPostProcessors.JwtRequestPostProcessor token;

    private String plainPassword;

    @BeforeEach
    public void setUp() {
        testUser = new User();
        testUser.setFirstName(faker.name().firstName());
        testUser.setLastName(faker.name().lastName());
        testUser.setEmail(faker.internet().emailAddress());
        plainPassword = faker.internet().password();
        testUser.setPassword(encoder.encode(plainPassword));
        repository.save(testUser);
        token = jwt().jwt(builder -> builder.subject(testUser.getEmail()));
    }

    @Test
    public void testRootPage() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(get("/")).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getContentAsString()).contains("Welcome to Spring!");
    }

    @Test
    public void testRegistration() throws Exception {
        User user = new User();
        user.setFirstName(faker.name().firstName());
        user.setLastName(faker.name().lastName());
        user.setEmail(faker.internet().emailAddress());
        plainPassword = faker.internet().password();
        user.setPassword(encoder.encode(plainPassword));
        MockHttpServletResponse response = mockMvc.perform(
                post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(user))
        ).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(201);
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
                        .with(token)
        ).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(403);
    }

    @Test
    public void tesGetUsersPage() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(
                get("/api/users")
                        .with(token)
        ).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(403);
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
        User user = new User();
        user.setFirstName(faker.name().firstName());
        user.setLastName(faker.name().lastName());
        MockHttpServletResponse response = mockMvc.perform(
                put("/api/users/" + testUser.getId())
                        .with(token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(user))
        ).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getContentAsString()).contains(user.getFirstName(), user.getLastName());
    }

    @Test
    public void testDeleteOwnPage() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(
                delete("/api/users/" + testUser.getId())
                        .with(token)
        ).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(204);
    }


//    public void testShow() throws Exception {
//        mockMvc.perform(get("/api/users/{id}", testUser.getId()))
//                .andExpect(status().isOk())
//                .andExpect(content().json(om.writeValueAsString(mapper.map(testUser))));
//    }

//    @Test
//    public void regUser() throws Exception {
//        mockMvc.perform(
//                get("/api/users")
//                .with(token)
//                )
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    public void testIndex() throws Exception {
//        mockMvc.perform(get("/api/users"))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    public void testShow() throws Exception {
//        mockMvc.perform(get("/api/users/{id}", testUser.getId()))
//                .andExpect(status().isOk())
//                .andExpect(content().json(om.writeValueAsString(mapper.map(testUser))));
//    }
//
//    @Test
//    public void testCreate() throws Exception {
//        User user = new User();
//        user.setFirstName(faker.name().firstName());
//        user.setLastName(faker.name().lastName());
//        user.setEmail(faker.internet().emailAddress());
//        user.setPassword(faker.internet().emailAddress());
//
//        MockHttpServletRequestBuilder request = post("/api/users")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(om.writeValueAsString(user));
//        mockMvc.perform(request).andExpect(status().isCreated());
//    }
//
//    @Test
//    public void testUpdate() throws Exception {
//        User user = new User();
//        user.setFirstName(faker.name().firstName());
//        user.setLastName(faker.name().lastName());
//
//        MockHttpServletRequestBuilder request = put("/api/users/{id}", testUser.getId())
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(om.writeValueAsString(user));
//
//        mockMvc.perform(request)
//                .andExpect(status().isOk());
//
//        User actualUser = repository.findById(testUser.getId()).get();
//
//        assertThat(actualUser.getFirstName()).isEqualTo(user.getFirstName());
//        assertThat(actualUser.getLastName()).isEqualTo(user.getLastName());
//    }
//
//    @Test
//    public void testDelete() throws Exception {
//
//        mockMvc.perform(delete("/api/users/{id}", testUser.getId()))
//                .andExpect(status().isNoContent());
//        assertThat(repository.findById(testUser.getId())).isEmpty();
//    }


}
