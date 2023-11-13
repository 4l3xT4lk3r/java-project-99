package hexlet.code.model;

import jakarta.persistence.Table;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    @NotNull
    @Email(message = "Wrong email format!")
    @Column(unique = true)
    private String email;

    @NotNull
    @Size(min = 3, message = "Minimal password length is about 3 symbols!")
    private String password;

    @LastModifiedDate
    private LocalDate updatedAt;

    @CreatedDate
    private LocalDate createdAt;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<GrantedAuthority>();
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
