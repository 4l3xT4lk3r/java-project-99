package hexlet.code.controller;

import hexlet.code.dto.AuthRequest;
import hexlet.code.util.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api")
public class AuthenticationController {
    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping(path = "/login")
    public String create(@RequestBody AuthRequest authRequest) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                authRequest.getUsername(), authRequest.getPassword() );
        authenticationManager.authenticate(authentication);
        return jwtUtils.generateToken(authRequest.getUsername());
    }
}
