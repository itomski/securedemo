package de.lubowiecki.securedemo;

import de.lubowiecki.securedemo.model.User;
import de.lubowiecki.securedemo.model.UserRole;
import de.lubowiecki.securedemo.model.UserStatus;
import de.lubowiecki.securedemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class SecuredemoApplication implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(SecuredemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        userRepository.deleteAll();
        User user = new User();
        user.setUsername("p.parker");
        user.setEmail("p.parker@shield.org");
        user.setPassword(passwordEncoder.encode("geheim"));
        user.setRole(UserRole.ADMIN);
        user.setStatus(UserStatus.ACTIVE);
        userRepository.save(user);
    }
}
