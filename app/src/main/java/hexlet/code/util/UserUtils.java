package hexlet.code.util;

import hexlet.code.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserUtils {

    @Autowired
    private UserRepository userRepository;

}
