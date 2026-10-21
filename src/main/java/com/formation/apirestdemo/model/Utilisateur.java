package com.formation.apirestdemo.model;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
/**
 * Entité JPA représentant un utilisateur.
 * Chaque instance correspond à une ligne dans la table "utilisateurs".
 */
@Entity
@Table(name = "utilisateurs")
public class Utilisateur {
    /**
     * Identifiant unique, généré automatiquement par la base de données.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * Nom de l'utilisateur. Ne peut pas être vide.
     */
    @NotBlank(message = "Le nom est obligatoire")
    @Column(nullable = false)
    private String nom;
    /**
     * Prénom de l'utilisateur. Ne peut pas être vide.
     */
    @NotBlank(message = "Le prénom est obligatoire")
    @Column(nullable = false)
    private String prenom;
    /**
     * Email de l'utilisateur. Obligatoire et unique en base.
     */
    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "L'email n'est pas valide")
    @Column(nullable = false, unique = true)
    private String email;
    /**
     * Âge de l'utilisateur. Doit être positif.
     */
    @Positive(message = "L'âge doit être positif")
    private Integer age;
// --- Constructeurs ---
/**
 * Constructeur sans arguments (requis par JPA).
 */
public Utilisateur() {
}
    /**
     * Constructeur avec tous les champs (sauf l'id, généré automatiquement).
     */
    public Utilisateur(String nom, String prenom, String email, Integer age) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.age = age;
    }
    // --- Getters et Setters ---
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public String getPrenom() {
        return prenom;
    }
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public Integer getAge() {
        return age;
    }
    public void setAge(Integer age) {
        this.age = age;
    }
    @Override
    public String toString() {
        return "Utilisateur{id=" + id + ", nom='" + nom + "', prenom='" + prenom + "', email='" + email + "'}";
    }
}
