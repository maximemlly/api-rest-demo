package com.formation.apirestdemo.controller;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
@RestController
@RequestMapping("/api/v1/bonjour")
public class BonjourController {
    @GetMapping
    public Map<String, String> bonjour() {
        return Map.of("message", "Bonjour le monde !");
    }
    @GetMapping("/{prenom}")
    public Map<String, String> bonjourPrenom(@PathVariable String prenom) {
        return Map.of("message", "Bonjour " + prenom + " !");
    }
    @PostMapping
    public Map<String, String> bienvenue(@RequestBody Map<String, String> corps) {
        return Map.of("message", "Bienvenue " + corps.get("prenom") + " !");
    }
}
