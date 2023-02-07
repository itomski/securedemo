package de.lubowiecki.securedemo.service;

import de.lubowiecki.securedemo.model.User;
import de.lubowiecki.securedemo.model.UserStatus;
import de.lubowiecki.securedemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> opt = userRepository.findByUsernameIgnoreCase(username);

        if(opt.isPresent()) {
            User u = opt.get();
            return org.springframework.security.core.userdetails.User
                    .withUsername(u.getUsername())
                    .password(u.getPassword())
                    .roles(u.getRole().toString())
                    .disabled(u.getStatus().equals(UserStatus.BLOCKED))
                    .build();
        };

        throw new RuntimeException("User nicht gefunden");
    }
}
