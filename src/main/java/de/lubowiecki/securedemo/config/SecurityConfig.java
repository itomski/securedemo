package de.lubowiecki.securedemo.config;

import de.lubowiecki.securedemo.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.server.session.WebSessionManager;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /*
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails admin = User.withUsername("admin")
                                .password(passwordEncoder().encode("geheim"))
                                .roles("ADMIN")
                                .build();

        UserDetails user = User.withUsername("p.parker")
                                .password(passwordEncoder().encode("geheim"))
                                .roles("USER")
                                .build();

        return new InMemoryUserDetailsManager(admin, user);
    }
    */

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // Request -> Server -> SecurityChain -> Controller -> Response
        http
            .csrf().disable()
            //.httpBasic()
            .formLogin()
            .and()
                .authorizeRequests()
                    .antMatchers("/admin/**").hasRole("ADMIN")
                    .antMatchers("/").permitAll()
                    .antMatchers("/login*").permitAll()
            .and()
                .logout().logoutUrl("/logout").deleteCookies("JSESSIONID");

        return http.build();
    }
}
