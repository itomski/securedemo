package de.lubowiecki.securedemo.config;

import de.lubowiecki.securedemo.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
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
@EnableScheduling
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
    public WebSecurityCustomizer webSecurityCustomizer() {
        // Diese Ordner sollen von der Security nicht beschützt werden
        return web -> web.ignoring().antMatchers("/css/**", "/img/**", "/js/**", "/webjars/**", "/favicon.ico");
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // Request -> Server -> SecurityChain -> Controller -> Response
        http
            .csrf().disable()
            //.httpBasic()
            .formLogin()
                .loginPage("/login")
                .failureUrl("/login/error")
                //.defaultSuccessUrl("/") // Weiterleitung nach dem Login
            .and()
                .authorizeRequests()
                    .antMatchers("/", "/login/**", "/register/**", "/activate/**", "/forgot/**", "/h2-console/**").permitAll() // Frei zugänglich
                    //.antMatchers("/admin/**").hasRole("ADMIN") // Freigabe nur mit einer bestimmten Role
                    .anyRequest().authenticated() // Alle anderen erfordern Anmeldung
            .and()
                .logout().logoutUrl("/logout")
                    .invalidateHttpSession(true); // Session wird ungültig
                    //.deleteCookies("JSESSIONID"); // Session-Cookie wird im Browser gelöscht

        http.headers().frameOptions().sameOrigin(); // Freigabe der Frames für H2-Datenbank

        return http.build();
    }
}
