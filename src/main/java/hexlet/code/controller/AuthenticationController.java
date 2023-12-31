package hexlet.code.controller;

import hexlet.code.dto.AuthRequest;
import hexlet.code.util.JWTUtils;
import io.sentry.Sentry;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/api")
@AllArgsConstructor
public class AuthenticationController {

    private JWTUtils jwtUtils;

    private AuthenticationManager authenticationManager;

    @Operation(summary = "Authenticate and authorize user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User authorized"),
        @ApiResponse(responseCode = "401", description = "User no authorized")
    })
    @PostMapping(path = "/login")
    public ResponseEntity<String> auth(
            @Parameter(description = "User data for authorize")
            @RequestBody AuthRequest authRequest) {
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    authRequest.getUsername(), authRequest.getPassword());

            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            List<GrantedAuthority> authorities = new ArrayList<>(authentication.getAuthorities());
            String token = jwtUtils.generateToken(authentication.getName(), authorities);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(token);
        } catch (Exception e) {
            Sentry.captureException(e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("invalid/username password");
        }
    }
}
