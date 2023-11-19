package hexlet.code.component;

import hexlet.code.mapper.UserMapper;
import hexlet.code.model.User;
import hexlet.code.model.UserRole;
import hexlet.code.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

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
        user = new User();
        user.setEmail("hexlet@example.com");
        user.setPassword(encoder.encode("qwerty"));
        user.setRole(UserRole.ADMIN);
        repository.save(user);
    }
}
