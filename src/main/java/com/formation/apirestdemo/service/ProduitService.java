package com.formation.apirestdemo.service;
import com.formation.apirestdemo.model.Produit;
import com.formation.apirestdemo.repository.ProduitRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.DoubleSummaryStatistics;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
/**
 * Service contenant la logique métier pour les produits.
 * Le service est le chef d'orchestre : il coordonne les appels
 * au repository et applique les règles métier.
 */
@Service
public class ProduitService {
    private final ProduitRepository produitRepository;
    private final NotificationService notificationService;
    /**
     * Injection de dépendance par constructeur.
     * Spring injecte automatiquement le ProduitRepository.
     */
    public ProduitService(ProduitRepository produitRepository,
                          @Qualifier("consoleNotificationService") NotificationService notificationService) {
        this.produitRepository = produitRepository;
        this.notificationService = notificationService;
    }
    /**
     * Retourne tous les produits.
     */
    public List<Produit> recupererTous() {
        return produitRepository.findAll();
    }
    /**
     * Retourne un produit par son ID.
     * @return Optional vide si le produit n'existe pas.
     */
    public Optional<Produit> recupererParId(Long id) {
        return produitRepository.findById(id);
    }
    /**
     * Crée un nouveau produit.
     * @param produit Le produit à créer (sans ID).
     * @return Le produit créé (avec ID généré).
     */
    public Produit creer(Produit produit) {
// On s'assure que l'ID est null pour forcer une création (pas une mise à jour)
        produit.setId(null);
        Produit produitCree = produitRepository.save(produit);
        notificationService.notifier("stock", "Nouveau produit : " + produit.getNom());
        return produitCree;
    }
    /**
     * Met à jour un produit existant.
     * @param id L'ID du produit à modifier.
     * @param produitModifie Les nouvelles valeurs.
     * @return Optional contenant le produit mis à jour, ou vide si l'ID n'existe
    pas.
     */
    public Optional<Produit> mettreAJour(Long id, Produit produitModifie) {
        return produitRepository.findById(id)
                .map(produitExistant -> {
                    produitExistant.setNom(produitModifie.getNom());
                    produitExistant.setDescription(produitModifie.getDescription());
                    produitExistant.setPrix(produitModifie.getPrix());
                    produitExistant.setStock(produitModifie.getStock());
                    return produitRepository.save(produitExistant);
                });
    }
    /**
     * Supprime un produit par son ID.
     * @return true si le produit existait et a été supprimé, false sinon.
     */
    public boolean supprimer(Long id) {
        if (produitRepository.existsById(id)) {
            produitRepository.deleteById(id);
            return true;
        }
        return false;
    }
    /**
     * Recherche des produits par nom (recherche partielle, insensible à la casse).
     */
    public List<Produit> rechercherParNom(String nom) {
        return produitRepository.findByNomContainingIgnoreCase(nom);
    }
    public List<Produit> filtrer(String nom, Double prixMin, Double prixMax) {
        return produitRepository.filtrer(nom, prixMin, prixMax);
    }
    public Map<String, Object> statistiques() {
        List<Produit> produits = produitRepository.findAll();
        DoubleSummaryStatistics prix = produits.stream()
                .mapToDouble(Produit::getPrix)
                .summaryStatistics();
        int stockTotal = produits.stream()
                .mapToInt(p -> p.getStock() == null ? 0 : p.getStock())
                .sum();
        boolean vide = prix.getCount() == 0;
        Map<String, Object> stats = new LinkedHashMap<>();
        stats.put("nombreTotal", prix.getCount());
        stats.put("prixMoyen", Math.round(prix.getAverage() * 100) / 100.0);
        stats.put("prixMin", vide ? null : prix.getMin());
        stats.put("prixMax", vide ? null : prix.getMax());
        stats.put("stockTotal", stockTotal);
        return stats;
    }
    public Optional<Produit> ajouterStock(Long id, int quantite) {
        return produitRepository.findById(id)
                .map(produit -> {
                    int stockActuel = produit.getStock() == null ? 0 : produit.getStock();
                    int nouveauStock = stockActuel + quantite;
                    if (nouveauStock < 0) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Le stock ne peut pas être négatif");
                    }
                    produit.setStock(nouveauStock);
                    return produitRepository.save(produit);
                });
    }
}