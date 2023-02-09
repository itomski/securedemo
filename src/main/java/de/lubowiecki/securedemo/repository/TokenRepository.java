package de.lubowiecki.securedemo.repository;

import de.lubowiecki.securedemo.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TokenRepository extends JpaRepository<Token, UUID> {
}
