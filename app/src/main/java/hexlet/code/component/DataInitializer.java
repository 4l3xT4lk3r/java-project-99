package hexlet.code.component;

import hexlet.code.dto.UserCreateDTO;
import hexlet.code.mapper.UserMapper;
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
        UserCreateDTO userData = new UserCreateDTO();
        userData.setEmail("hexlet@example.com");
        userData.setPassword(encoder.encode("qwerty"));
        var user = mapper.map(userData);
        repository.save(user);
    }
}
