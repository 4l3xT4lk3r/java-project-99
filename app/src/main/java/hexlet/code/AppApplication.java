package hexlet.code;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import net.datafaker.Faker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@OpenAPIDefinition(
        info = @Info(
                title = "Task Manager API",
                version = "1.0",
                description = "Task Manager API"
        )
)
@EnableJpaAuditing
@SpringBootApplication
public class AppApplication {
    @Bean
    public Faker getFaker() {
        return new Faker();
    }
    public static void main(String[] args) {
        SpringApplication.run(AppApplication.class, args);
    }

}
