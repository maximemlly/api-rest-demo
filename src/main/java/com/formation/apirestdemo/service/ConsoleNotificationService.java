package com.formation.apirestdemo.service;
import org.springframework.stereotype.Service;
/**
 * Implémentation qui affiche les notifications dans la console.
 */
@Service
public class ConsoleNotificationService implements NotificationService {
    @Override
    public void notifier(String destinataire, String message) {
        // Remplacé par un logger dans la Partie 4b
        System.out.println("[NOTIFICATION] " + destinataire + " : " + message);
    }
}
