package hexlet.code.utils;

import hexlet.code.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Getter
@Component
public class UserUtil {

    @Autowired
    private Faker faker;

    @Autowired
    private PasswordEncoder encoder;

    String plainPassword;
    public User makeUser() {
        User user = new User();
        user.setFirstName(faker.name().firstName());
        user.setLastName(faker.name().lastName());
        user.setEmail(faker.internet().emailAddress());
        plainPassword = faker.internet().password();
        user.setPassword(encoder.encode(plainPassword));
        return user;
    }
}
