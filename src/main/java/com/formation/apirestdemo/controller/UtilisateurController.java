package com.formation.apirestdemo.controller;
import com.formation.apirestdemo.model.Utilisateur;
import com.formation.apirestdemo.service.UtilisateurService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
/**
 * Contrôleur REST pour les utilisateurs (Exercice 2).
 */
@RestController
@RequestMapping("/api/v1/utilisateurs")
public class UtilisateurController {
    private final UtilisateurService utilisateurService;
    public UtilisateurController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }
    // ============================================================
// GET /api/v1/utilisateurs — Lister tous les utilisateurs
// ============================================================
    @GetMapping
    public ResponseEntity<List<Utilisateur>> listerTous() {
        return ResponseEntity.ok(utilisateurService.recupererTous()); // 200 OK
    }
    // ============================================================
// GET /api/v1/utilisateurs/{id} — Obtenir un utilisateur par son ID
// ============================================================
    @GetMapping("/{id}")
    public ResponseEntity<Utilisateur> obtenirParId(@PathVariable Long id) {
        return utilisateurService.recupererParId(id)
                .map(ResponseEntity::ok) // 200 OK si trouvé
                .orElse(ResponseEntity.notFound().build()); // 404 si non trouvé
    }
    // ============================================================
// POST /api/v1/utilisateurs — Créer un nouvel utilisateur
// ============================================================
    @PostMapping
    public ResponseEntity<Utilisateur> creer(@Valid @RequestBody Utilisateur utilisateur) {
        return ResponseEntity
                .status(HttpStatus.CREATED) // 201 Created
                .body(utilisateurService.creer(utilisateur));
    }
    // ============================================================
// PUT /api/v1/utilisateurs/{id} — Mettre à jour un utilisateur
// ============================================================
    @PutMapping("/{id}")
    public ResponseEntity<Utilisateur> mettreAJour(@PathVariable Long id,
                                                   @Valid @RequestBody Utilisateur utilisateur)
    {
        return utilisateurService.mettreAJour(id, utilisateur)
                .map(ResponseEntity::ok) // 200 OK si mis à jour
                .orElse(ResponseEntity.notFound().build()); // 404 si non trouvé
    }
    // ============================================================
// DELETE /api/v1/utilisateurs/{id} — Supprimer un utilisateur
// ============================================================
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(@PathVariable Long id) {
        if (utilisateurService.supprimer(id)) {
            return ResponseEntity.noContent().build(); // 204 No Content
        }
        return ResponseEntity.notFound().build(); // 404 si non trouvé
    }
}
