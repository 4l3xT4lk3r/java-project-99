package hexlet.code.service;

import hexlet.code.dto.user.UserCreateDTO;
import hexlet.code.dto.user.UserDTO;
import hexlet.code.dto.user.UserUpdateDTO;
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
        User user = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with " + id + " not found!"));
        return mapper.map(user);
    }

    public UserDTO create(UserCreateDTO userData) {
        User user = mapper.map(userData);
        user.setPassword(passwordEncoder.encode(userData.getPassword()));
        repository.save(user);
        return mapper.map(user);
    }

    public UserDTO update(UserUpdateDTO userData, Long id) {
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
        User user = repository.findById(id).orElse(null);
        if (user != null) {
            if (user.getTasks().isEmpty()) {
                repository.deleteById(id);
            } else {
                throw new RuntimeException();
            }
        }
    }
}
