package com.formation.apirestdemo.service;
import com.formation.apirestdemo.model.Utilisateur;
import com.formation.apirestdemo.repository.UtilisateurRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Optional;
/**
 * Service contenant la logique métier pour les utilisateurs.
 */
@Service
public class UtilisateurService {
    private final UtilisateurRepository utilisateurRepository;
    /**
     * Injection de dépendance par constructeur.
     */
    public UtilisateurService(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }
    /**
     * Retourne tous les utilisateurs.
     */
    public List<Utilisateur> recupererTous() {
        return utilisateurRepository.findAll();
    }
    /**
     * Retourne un utilisateur par son ID.
     * @return Optional vide si l'utilisateur n'existe pas.
     */
    public Optional<Utilisateur> recupererParId(Long id) {
        return utilisateurRepository.findById(id);
    }
    /**
     * Crée un nouvel utilisateur.
     * @throws ResponseStatusException 409 si l'email est déjà utilisé.
     */
    public Utilisateur creer(Utilisateur utilisateur) {
// On s'assure que l'ID est null pour forcer une création (pas une mise à jour)
        utilisateur.setId(null);
        verifierEmailLibre(utilisateur.getEmail(), null);
        return utilisateurRepository.save(utilisateur);
    }
    /**
     * Met à jour un utilisateur existant.
     * @return Optional contenant l'utilisateur mis à jour, ou vide si l'ID n'existe pas.
     * @throws ResponseStatusException 409 si l'email appartient à un autre utilisateur.
     */
    public Optional<Utilisateur> mettreAJour(Long id, Utilisateur utilisateurModifie) {
        return utilisateurRepository.findById(id)
                .map(utilisateurExistant -> {
                    verifierEmailLibre(utilisateurModifie.getEmail(), id);
                    utilisateurExistant.setNom(utilisateurModifie.getNom());
                    utilisateurExistant.setPrenom(utilisateurModifie.getPrenom());
                    utilisateurExistant.setEmail(utilisateurModifie.getEmail());
                    utilisateurExistant.setAge(utilisateurModifie.getAge());
                    return utilisateurRepository.save(utilisateurExistant);
                });
    }
    /**
     * Supprime un utilisateur par son ID.
     * @return true si l'utilisateur existait et a été supprimé, false sinon.
     */
    public boolean supprimer(Long id) {
        if (utilisateurRepository.existsById(id)) {
            utilisateurRepository.deleteById(id);
            return true;
        }
        return false;
    }
    /**
     * Règle métier : un email ne peut appartenir qu'à un seul utilisateur.
     * @param idActuel l'ID de l'utilisateur modifié (null en création), qui a le droit de garder son email.
     */
    private void verifierEmailLibre(String email, Long idActuel) {
        utilisateurRepository.findByEmail(email)
                .filter(u -> !u.getId().equals(idActuel))
                .ifPresent(u -> {
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "Cet email est déjà utilisé");
                });
    }
}
