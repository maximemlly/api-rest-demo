package com.formation.apirestdemo.service;
/**
 * Contrat d'envoi de notifications.
 * ProduitService dépend de cette interface, pas d'une implémentation précise (principe DIP).
 */
public interface NotificationService {
    void notifier(String destinataire, String message);
}
