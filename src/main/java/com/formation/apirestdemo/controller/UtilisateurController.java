package com.formation.apirestdemo.controller;
import com.formation.apirestdemo.model.Utilisateur;
import com.formation.apirestdemo.service.UtilisateurService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/v1/utilisateurs")
public class UtilisateurController {
    private final UtilisateurService utilisateurService;
    public UtilisateurController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }
    @GetMapping
    public ResponseEntity<List<Utilisateur>> listerTous() {
        return ResponseEntity.ok(utilisateurService.recupererTous());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Utilisateur> obtenirParId(@PathVariable Long id) {
        return utilisateurService.recupererParId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PostMapping
    public ResponseEntity<Utilisateur> creer(@Valid @RequestBody Utilisateur utilisateur) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(utilisateurService.creer(utilisateur));
    }
    @PutMapping("/{id}")
    public ResponseEntity<Utilisateur> mettreAJour(@PathVariable Long id,
                                                   @Valid @RequestBody Utilisateur utilisateur)
    {
        return utilisateurService.mettreAJour(id, utilisateur)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(@PathVariable Long id) {
        if (utilisateurService.supprimer(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
