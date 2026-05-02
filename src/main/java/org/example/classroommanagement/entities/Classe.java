package org.example.classroommanagement.entities;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Entity representing an academic class in the classroom management system.
 * Each class has a level (niveau), title, and maintains bidirectional relationships
 * with both CoursClassroom and Utilisateur entities.
 * 
 * Validates Requirements: 2.1, 2.2, 2.3, 2.4, 9.1, 9.4, 9.5, 11.2, 11.5, 11.6, 11.7
 */
@Entity
public class Classe implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codeClasse;
    
    private String titre;
    
    @Enumerated(EnumType.STRING)
    private Niveau niveau;
    
    @OneToMany(mappedBy = "classe", cascade = CascadeType.ALL)
    private Set<CoursClassroom> coursClassrooms = new HashSet<>();
    
    @OneToMany(mappedBy = "classe")
    private Set<Utilisateur> utilisateurs = new HashSet<>();
    
    // Constructors
    
    /**
     * Default constructor required by JPA.
     */
    public Classe() {
    }
    
    /**
     * Constructor with titre and niveau.
     * 
     * @param titre the title of the class
     * @param niveau the academic level of the class
     */
    public Classe(String titre, Niveau niveau) {
        this.titre = titre;
        this.niveau = niveau;
    }
    
    // Getters and Setters
    
    public Integer getCodeClasse() {
        return codeClasse;
    }
    
    public void setCodeClasse(Integer codeClasse) {
        this.codeClasse = codeClasse;
    }
    
    public String getTitre() {
        return titre;
    }
    
    public void setTitre(String titre) {
        this.titre = titre;
    }
    
    public Niveau getNiveau() {
        return niveau;
    }
    
    public void setNiveau(Niveau niveau) {
        this.niveau = niveau;
    }
    
    public Set<CoursClassroom> getCoursClassrooms() {
        return coursClassrooms;
    }
    
    public void setCoursClassrooms(Set<CoursClassroom> coursClassrooms) {
        this.coursClassrooms = coursClassrooms;
    }
    
    public Set<Utilisateur> getUtilisateurs() {
        return utilisateurs;
    }
    
    public void setUtilisateurs(Set<Utilisateur> utilisateurs) {
        this.utilisateurs = utilisateurs;
    }
    
    // Helper methods for bidirectional relationship management
    
    /**
     * Helper method to add a course to this class while maintaining bidirectional relationship.
     * 
     * @param cours the course to add
     */
    public void addCoursClassroom(CoursClassroom cours) {
        coursClassrooms.add(cours);
        cours.setClasse(this);
    }
    
    /**
     * Helper method to remove a course from this class while maintaining bidirectional relationship.
     * 
     * @param cours the course to remove
     */
    public void removeCoursClassroom(CoursClassroom cours) {
        coursClassrooms.remove(cours);
        cours.setClasse(null);
    }
    
    /**
     * Helper method to add a user to this class while maintaining bidirectional relationship.
     * 
     * @param utilisateur the user to add
     */
    public void addUtilisateur(Utilisateur utilisateur) {
        utilisateurs.add(utilisateur);
        utilisateur.setClasse(this);
    }
    
    /**
     * Helper method to remove a user from this class while maintaining bidirectional relationship.
     * 
     * @param utilisateur the user to remove
     */
    public void removeUtilisateur(Utilisateur utilisateur) {
        utilisateurs.remove(utilisateur);
        utilisateur.setClasse(null);
    }
    
    @Override
    public String toString() {
        return "Classe{" +
                "codeClasse=" + codeClasse +
                ", titre='" + titre + '\'' +
                ", niveau=" + niveau +
                ", coursCount=" + coursClassrooms.size() +
                ", utilisateurCount=" + utilisateurs.size() +
                '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Classe classe = (Classe) o;
        return codeClasse != null && codeClasse.equals(classe.codeClasse);
    }
    
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
