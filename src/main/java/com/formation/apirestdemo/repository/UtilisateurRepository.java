package com.formation.apirestdemo.repository;
import com.formation.apirestdemo.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
/**
 * Repository pour l'entité Utilisateur.
 * Les méthodes CRUD (findAll, findById, save, deleteById...) sont héritées de JpaRepository.
 */
@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    /**
     * Recherche un utilisateur par son email (unique).
     * Équivalent SQL : SELECT * FROM utilisateurs WHERE email = ?
     */
    Optional<Utilisateur> findByEmail(String email);
}
