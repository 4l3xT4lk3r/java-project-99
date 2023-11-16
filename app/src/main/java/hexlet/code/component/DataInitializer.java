package hexlet.code.component;

import hexlet.code.dto.UserCreateDTO;
import hexlet.code.mapper.UserMapper;
import hexlet.code.model.User;
import hexlet.code.model.UserRole;
import hexlet.code.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class DataInitializer implements ApplicationRunner {
    @Autowired
    private UserRepository repository;

    @Autowired
    private UserMapper mapper;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        User user = new User();
        user.setFirstName("Ellen");
        user.setLastName("Ripley");
        user.setEmail("e.ripley@weyland.com");
        user.setPassword(encoder.encode("alien"));
        user.setRole(UserRole.USER);
        repository.save(user);

        user = new User();
        user.setEmail("hexlet@example.com");
        user.setPassword(encoder.encode("qwerty"));
        user.setRole(UserRole.ADMIN);
        repository.save(user);

        user = new User();
        user.setFirstName("John");
        user.setLastName("Wayne");
        user.setEmail("j.wayne@hollywood.com");
        user.setPassword(encoder.encode("western"));
        user.setRole(UserRole.USER);
        repository.save(user);
    }
}
