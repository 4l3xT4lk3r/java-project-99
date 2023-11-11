package hexlet.code.service;

import hexlet.code.dto.UserCreateDTO;
import hexlet.code.dto.UserDTO;
import hexlet.code.dto.UserUpdateDTO;
import hexlet.code.exception.ResourceNotFoundException;
import hexlet.code.mapper.UserMapper;
import hexlet.code.model.User;
import hexlet.code.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    @Autowired
    private UserMapper mapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    List<UserDTO> getAll() {
        return repository.findAll().stream().map(u -> mapper.map(u)).toList();
    }

    UserDTO findById(Long id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with " + id + " not found!"));
        return mapper.map(user);
    }

    UserDTO create(UserCreateDTO userData) {
        User user = mapper.map(userData);
        user.setPassword(passwordEncoder.encode(userData.getPassword()));
        repository.save(user);
        return mapper.map(user);
    }

    UserDTO update(UserUpdateDTO userData, Long id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with " + id + " not found!"));
        user.setPassword(passwordEncoder.encode(userData.getPassword().get()));
        mapper.update(userData, user);
        repository.save(user);
        return mapper.map(user);
    }

    void delete(Long id) {
        repository.deleteById(id);
    }

}
