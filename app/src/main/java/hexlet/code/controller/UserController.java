package hexlet.code.controller;

import hexlet.code.dto.user.UserCreateDTO;
import hexlet.code.dto.user.UserDTO;
import hexlet.code.dto.user.UserUpdateDTO;
import hexlet.code.service.CustomUserDetailsService;
import hexlet.code.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/users")
public class UserController {

    private UserService service;

    private CustomUserDetailsService customUserDetailsService;

    @GetMapping(path = "")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<List<UserDTO>> index() {
        List<UserDTO> list = service.getAll();
        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(list.size()))
                .body(list);
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN') || authentication.getName() == @UserService.findById(#id).getEmail()")
    @GetMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO show(@PathVariable(name = "id") long id) {
        return service.findById(id);
    }

    @PostMapping(path = "")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO create(@Valid @RequestBody UserCreateDTO userData) {
        return service.create(userData);
    }

    @PutMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('SCOPE_ADMIN') || authentication.getName() == @UserService.findById(#id).getEmail()")
    public UserDTO update(@Valid @RequestBody UserUpdateDTO userData, @PathVariable long id) {
        return service.update(userData, id);
    }

    @DeleteMapping(path = "/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN') || authentication.getName() == @UserService.findById(#id).getEmail()")
    public ResponseEntity<String> delete(@PathVariable long id) {
        try {
            service.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Cant delete - user have tasks!");
        }
    }

}
