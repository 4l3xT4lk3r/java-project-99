package hexlet.code.component;

import hexlet.code.model.Label;
import hexlet.code.model.TaskStatus;
import hexlet.code.model.User;
import hexlet.code.repository.TaskStatusRepository;
import hexlet.code.repository.UserRepository;
import hexlet.code.repository.LabelRepository;
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

    private LabelRepository labelRepository;

    private PasswordEncoder encoder;


    @Override
    public void run(ApplicationArguments args) throws Exception {

        List<Map<String, String>> users = List.of(
                Map.of("email", "hexlet@example.com", "password", "qwerty"),
                Map.of("email", "e.ripley@weyland.com", "password", "alien"),
                Map.of("email", "j.wayne@hollywood.com", "password", "western"));
        users.forEach((userData) -> {
            User user = new User();
            user.setEmail(userData.get("email"));
            user.setPassword(encoder.encode(userData.get("password")));
            userRepository.save(user);
        });

        List<String> taskStatuses = List.of("draft", "to_review", "to_be_fixed", "to_publish", "published");
        taskStatuses.forEach(ts -> {
            TaskStatus t = new TaskStatus();
            t.setName(ts);
            t.setSlug(ts);
            taskStatusRepository.save(t);
        });

        List<String> labels = List.of("feature", "bug");
        labels.forEach(label -> {
            Label l = new Label();
            l.setName(label);
            labelRepository.save(l);
        });
    }
}
