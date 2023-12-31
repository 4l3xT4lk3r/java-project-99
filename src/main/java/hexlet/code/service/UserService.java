package hexlet.code.service;

import hexlet.code.dto.UserDTO;
import hexlet.code.exception.ResourceNotFoundException;
import hexlet.code.mapper.UserMapper;
import hexlet.code.model.User;
import hexlet.code.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("UserService")
@AllArgsConstructor
public class UserService {

    private UserRepository repository;

    private UserMapper mapper;

    private PasswordEncoder passwordEncoder;

    public List<UserDTO> getAll() {
        return repository.findAll().stream().map(u -> mapper.map(u)).toList();
    }

    public UserDTO findById(Long id) {
        User user = repository.findById(id).orElseThrow();
        return mapper.map(user);
    }

    public UserDTO create(UserDTO userData) {
        User user = mapper.map(userData);
        user.setPassword(passwordEncoder.encode(userData.getPassword().get()));
        repository.save(user);
        return mapper.map(user);
    }

    public UserDTO update(UserDTO userData, Long id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with " + id + " not found!"));
        mapper.update(userData, user);
        if (userData.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(userData.getPassword().get()));
        }
        repository.save(user);
        return mapper.map(user);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
