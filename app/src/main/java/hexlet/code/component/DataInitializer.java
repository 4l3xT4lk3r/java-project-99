package hexlet.code.component;

import hexlet.code.model.TaskStatus;
import hexlet.code.model.User;
import hexlet.code.model.UserRole;
import hexlet.code.repository.TaskStatusRepository;
import hexlet.code.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@AllArgsConstructor
public class DataInitializer implements ApplicationRunner {

    private UserRepository userRepository;

    private TaskStatusRepository taskStatusRepository;

    private PasswordEncoder encoder;


    @Override
    public void run(ApplicationArguments args) throws Exception {

        List<Map<String, String>> users = List.of(
                Map.of("email", "hexlet@example.com", "password", "qwerty", "role", "ADMIN"),
                Map.of("email", "e.ripley@weyland.com", "password", "alien", "role", "USER"),
                Map.of("email", "j.wayne@hollywood.com", "password", "western", "role", "USER"));
        users.forEach((userData) -> {
            User user = new User();
            user.setEmail(userData.get("email"));
            user.setPassword(encoder.encode(userData.get("password")));
            user.setRole(userData.get("role").equals("ADMIN") ? UserRole.ADMIN : UserRole.USER);
            userRepository.save(user);
        });

        List<String> taskStatuses = List.of("draft", "to_review", "to_be_fixed", "to_publish", "published");
        taskStatuses.forEach(ts -> {
            TaskStatus t = new TaskStatus();
            t.setName(ts);
            t.setSlug(ts);
            taskStatusRepository.save(t);
        });
    }
}
