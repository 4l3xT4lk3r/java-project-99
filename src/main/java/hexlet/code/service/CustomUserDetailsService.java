package hexlet.code.service;

import hexlet.code.repository.UserRepository;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsManager {

    @Autowired
    private UserRepository repository;

    @Override
    public void createUser(UserDetails user) {
        throw new NotImplementedException();
    }

    @Override
    public void updateUser(UserDetails user) {
        throw new NotImplementedException();
    }

    @Override
    public void deleteUser(String username) {
        throw new NotImplementedException();
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        throw new NotImplementedException();
    }

    @Override
    public boolean userExists(String username) {
        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User with email " + email + " not found."));
    }
}
