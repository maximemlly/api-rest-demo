package com.formation.apirestdemo.controller;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
/**
 * Contrôleur REST "Hello World" (Exercice 1).
 * Retourne du JSON simple grâce à des Map<String, String>.
 */
@RestController
@RequestMapping("/api/v1/bonjour")
public class BonjourController {
    // ============================================================
// GET /api/v1/bonjour — Message de bienvenue générique
// ============================================================
    @GetMapping
    public Map<String, String> bonjour() {
        return Map.of("message", "Bonjour le monde !");
    }
    // ============================================================
// GET /api/v1/bonjour/{prenom} — Message personnalisé
// ============================================================
    @GetMapping("/{prenom}")
    public Map<String, String> bonjourPrenom(@PathVariable String prenom) {
        return Map.of("message", "Bonjour " + prenom + " !");
    }
    // ============================================================
// POST /api/v1/bonjour — Accepte {"prenom": "Jean"}
// ============================================================
    @PostMapping
    public Map<String, String> bienvenue(@RequestBody Map<String, String> corps) {
        return Map.of("message", "Bienvenue " + corps.get("prenom") + " !");
    }
}
