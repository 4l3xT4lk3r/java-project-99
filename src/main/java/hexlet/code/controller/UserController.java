package hexlet.code.controller;

import hexlet.code.dto.UserDTO;
import hexlet.code.service.CustomUserDetailsService;
import hexlet.code.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Get list of all users")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Show users"),
        @ApiResponse(responseCode = "403", description = "No grants for show users")
    })
    @GetMapping(path = "")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<UserDTO>> index() {
        List<UserDTO> list = service.getAll();
        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(list.size()))
                .body(list);
    }

    @Operation(summary = "Get specific user by his id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User found"),
        @ApiResponse(responseCode = "403", description = "No grants for show user"),
        @ApiResponse(responseCode = "404", description = "User with that id not found")
    })
    @PreAuthorize("authentication.getName() == @UserService.findById(#id).getEmail().get()")
    @GetMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO show(
            @Parameter(description = "Id of user to be found")
            @PathVariable long id) {
        return service.findById(id);
    }

    @Operation(summary = "Create new user")
    @ApiResponse(responseCode = "201", description = "User created")
    @PostMapping(path = "")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO create(
            @Parameter(description = "User data to create")
            @Valid
            @RequestBody UserDTO userData) {
        return service.create(userData);
    }

    @Operation(summary = "Update user by his id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User updated"),
        @ApiResponse(responseCode = "403", description = "No grants for user update"),
        @ApiResponse(responseCode = "404", description = "User with that id not found")
    })
    @PutMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("authentication.getName() == @UserService.findById(#id).getEmail().get()")
    public UserDTO update(
            @Parameter(description = "User data to update")
            @Valid
            @RequestBody UserDTO userData,
            @Parameter(description = "Id of user to be updated")
            @PathVariable long id) {
        return service.update(userData, id);
    }

    @Operation(summary = "Delete user by his id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User deleted"),
        @ApiResponse(responseCode = "403", description = "No grant to delete user"),
        @ApiResponse(responseCode = "404", description = "User with that id not found")
    })
    @DeleteMapping(path = "/{id}")
    @PreAuthorize("authentication.getName() == @UserService.findById(#id).getEmail().get()")
    public ResponseEntity<String> delete(
            @Parameter(description = "Id of user to be deleted")
            @PathVariable long id) {
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

}
