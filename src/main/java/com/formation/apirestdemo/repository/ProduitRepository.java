package com.formation.apirestdemo.repository;
import com.formation.apirestdemo.model.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
/**
 * Repository pour l'entité Produit.
 * En héritant de JpaRepository, Spring génère automatiquement
 * les méthodes CRUD : findAll(), findById(), save(), deleteById(), etc.
 *
 * Pas besoin d'écrire l'implémentation : Spring Data le fait pour nous.
 */
@Repository
public interface ProduitRepository extends JpaRepository<Produit, Long> {
    /**
     * Recherche des produits dont le nom contient la chaîne donnée (insensible à la
     casse).
     * Spring Data génère automatiquement la requête SQL à partir du nom de la
     méthode.
     * Équivalent SQL : SELECT * FROM produits WHERE LOWER(nom) LIKE LOWER('%nom%')
     */
    List<Produit> findByNomContainingIgnoreCase(String nom);
    /**
     * Recherche des produits dont le prix est inférieur ou égal au montant donné.
     * Équivalent SQL : SELECT * FROM produits WHERE prix <= ?
     */
    List<Produit> findByPrixLessThanEqual(Double prix);
    /**
     * Recherche multi-critères : chaque paramètre null est ignoré.
     * Requête écrite en JPQL (on manipule l'entité Produit, pas la table).
     */
    @Query("SELECT p FROM Produit p WHERE " +
           "(:nom IS NULL OR LOWER(p.nom) LIKE LOWER(CONCAT('%', :nom, '%'))) AND " +
           "(:prixMin IS NULL OR p.prix >= :prixMin) AND " +
           "(:prixMax IS NULL OR p.prix <= :prixMax)")
    List<Produit> filtrer(@Param("nom") String nom,
                          @Param("prixMin") Double prixMin,
                          @Param("prixMax") Double prixMax);
}