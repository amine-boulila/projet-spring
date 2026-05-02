package org.example.classroommanagement.entities;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * Entity representing a user (student or administrator) in the classroom management system.
 * Each user can be assigned to one class (many-to-one relationship with Classe).
 * 
 * Validates Requirements: 1.1, 1.2, 1.3, 11.1, 11.5, 11.6
 */
@Entity
public class Utilisateur implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUtilisateur;
    
    private String prenom;
    
    private String nom;
    
    private String password;
    
    @ManyToOne
    private Classe classe;
    
    // Constructors
    
    /**
     * Default constructor required by JPA.
     */
    public Utilisateur() {
    }
    
    /**
     * Constructor with all fields except ID (auto-generated).
     * 
     * @param prenom the user's first name
     * @param nom the user's last name
     * @param password the user's password
     */
    public Utilisateur(String prenom, String nom, String password) {
        this.prenom = prenom;
        this.nom = nom;
        this.password = password;
    }
    
    /**
     * Full constructor including classe assignment.
     * 
     * @param prenom the user's first name
     * @param nom the user's last name
     * @param password the user's password
     * @param classe the class to which the user belongs
     */
    public Utilisateur(String prenom, String nom, String password, Classe classe) {
        this.prenom = prenom;
        this.nom = nom;
        this.password = password;
        this.classe = classe;
    }
    
    // Getters and Setters
    
    public Integer getIdUtilisateur() {
        return idUtilisateur;
    }
    
    public void setIdUtilisateur(Integer idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }
    
    public String getPrenom() {
        return prenom;
    }
    
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    
    public String getNom() {
        return nom;
    }
    
    public void setNom(String nom) {
        this.nom = nom;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public Classe getClasse() {
        return classe;
    }
    
    public void setClasse(Classe classe) {
        this.classe = classe;
    }
    
    @Override
    public String toString() {
        return "Utilisateur{" +
                "idUtilisateur=" + idUtilisateur +
                ", prenom='" + prenom + '\'' +
                ", nom='" + nom + '\'' +
                ", classe=" + (classe != null ? classe.getCodeClasse() : "null") +
                '}';
    }
}
