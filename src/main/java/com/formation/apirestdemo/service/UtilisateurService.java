package com.formation.apirestdemo.service;
import com.formation.apirestdemo.model.Utilisateur;
import com.formation.apirestdemo.repository.UtilisateurRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Optional;
@Service
public class UtilisateurService {
    private final UtilisateurRepository utilisateurRepository;
    public UtilisateurService(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }
    public List<Utilisateur> recupererTous() {
        return utilisateurRepository.findAll();
    }
    public Optional<Utilisateur> recupererParId(Long id) {
        return utilisateurRepository.findById(id);
    }
    public Utilisateur creer(Utilisateur utilisateur) {
        utilisateur.setId(null);
        verifierEmailLibre(utilisateur.getEmail(), null);
        return utilisateurRepository.save(utilisateur);
    }
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
    public boolean supprimer(Long id) {
        if (utilisateurRepository.existsById(id)) {
            utilisateurRepository.deleteById(id);
            return true;
        }
        return false;
    }
    private void verifierEmailLibre(String email, Long idActuel) {
        utilisateurRepository.findByEmail(email)
                .filter(u -> !u.getId().equals(idActuel))
                .ifPresent(u -> {
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "Cet email est déjà utilisé");
                });
    }
}
