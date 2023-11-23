package hexlet.code.config;

import hexlet.code.service.CustomUserDetailsService;
import lombok.AllArgsConstructor;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@AllArgsConstructor
public class SecurityConfig {

    private final JwtDecoder jwtDecoder;

    private final PasswordEncoder encoder;

    private CustomUserDetailsService userDetailsService;

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class).build();
    }

    @Bean
    public AuthenticationProvider daoAuthProvider(AuthenticationManagerBuilder auth) {
        var provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(encoder);
        return provider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, HandlerMappingIntrospector introspector)
            throws Exception {
        var mvcMatcherBuilder = new MvcRequestMatcher.Builder(introspector);
        http
                .csrf(AbstractHttpConfigurer::disable)
                .headers(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((auth) -> auth
//                        .requestMatchers(toH2Console())
//                        .permitAll()
//                        .requestMatchers(PathRequest.toH2Console())
//                        .permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern("/swagger-ui.html"))
                        .permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern("/swagger-ui/**"))
                        .permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern("/v3/api-docs/swagger-config"))
                        .permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern("/v3/api-docs/**"))
                        .permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern("/"))
                        .permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern("/index.html"))
                        .permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern("/welcome"))
                        .permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.GET, "/assets/**"))
                        .permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.POST, "/api/login"))
                        .permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.POST, "/api/users"))
                        .permitAll()
                        .anyRequest()
                        .authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2ResourceServer((rs) -> rs.jwt((jwt) -> jwt.decoder(jwtDecoder)))
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }
}
