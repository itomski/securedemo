package de.lubowiecki.securedemo.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "app_users") // User Objekt mit der Tabelle app_users aus der DB verbinden
public class User implements Serializable {

    @Id
    @GeneratedValue
    // @Column(name = "knr") // id mit der Spalte knr in der DB verbinden
    private long id;

    @Column(length = 50, unique = true)
    private String username;

    @Column(length = 50, unique = true)
    private String email;

    @Column(length = 100)
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    public User() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }
}
