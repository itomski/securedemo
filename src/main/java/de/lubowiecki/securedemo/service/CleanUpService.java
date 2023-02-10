package de.lubowiecki.securedemo.service;

import de.lubowiecki.securedemo.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CleanUpService {

    @Autowired
    private TokenRepository tokenRepository;

    //@Scheduled(cron = "@hourly") // @yearly, @daily, @midnight, @hourly
    @Scheduled(cron = "0 */5 * * * *") // sec min std tag monat wochentag
    public void deleteExpiredTokens() {
        tokenRepository.deleteByCreatedAtBefore(LocalDateTime.now().minusDays(7));
        System.out.println("Clean UP");
    }
}
