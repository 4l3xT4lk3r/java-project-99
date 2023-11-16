package hexlet.code.controller;

import hexlet.code.dto.AuthRequest;
import hexlet.code.util.JWTUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/api")
@AllArgsConstructor
public class AuthenticationController {

    private JWTUtils jwtUtils;

    private AuthenticationManager authenticationManager;

    @PostMapping(path = "/login")

    public ResponseEntity<String> create(@RequestBody AuthRequest authRequest) {
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    authRequest.getUsername(), authRequest.getPassword() );

            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            List<GrantedAuthority> authorities = new ArrayList<>(authentication.getAuthorities());
            String token = jwtUtils.generateToken(authentication.getName(), authorities);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(token);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("invalid/username password");
        }
    }
}
