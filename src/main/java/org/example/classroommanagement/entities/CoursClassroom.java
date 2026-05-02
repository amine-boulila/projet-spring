package org.example.classroommanagement.entities;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * Entity representing a course in the classroom management system.
 * Each course has a specialty, name, hours, and archive status.
 * It maintains a many-to-one relationship with Classe (one course belongs to one class).
 * 
 * Validates Requirements: 3.1, 3.2, 3.3, 3.4, 9.1, 9.4, 11.3, 11.5, 11.6, 11.7
 */
@Entity
public class CoursClassroom implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCours;
    
    @Enumerated(EnumType.STRING)
    private Specialite specialite;
    
    private String nom;
    
    private Integer nbHeures;
    
    private Boolean archive = false;
    
    @ManyToOne
    private Classe classe;
    
    // Constructors
    
    /**
     * Default constructor required by JPA.
     */
    public CoursClassroom() {
    }
    
    /**
     * Constructor with specialite, nom, and nbHeures.
     * Archive is set to false by default.
     * 
     * @param specialite the specialty of the course
     * @param nom the name of the course
     * @param nbHeures the number of hours for the course
     */
    public CoursClassroom(Specialite specialite, String nom, Integer nbHeures) {
        this.specialite = specialite;
        this.nom = nom;
        this.nbHeures = nbHeures;
        this.archive = false;
    }
    
    // Getters and Setters
    
    public Integer getIdCours() {
        return idCours;
    }
    
    public void setIdCours(Integer idCours) {
        this.idCours = idCours;
    }
    
    public Specialite getSpecialite() {
        return specialite;
    }
    
    public void setSpecialite(Specialite specialite) {
        this.specialite = specialite;
    }
    
    public String getNom() {
        return nom;
    }
    
    public void setNom(String nom) {
        this.nom = nom;
    }
    
    public Integer getNbHeures() {
        return nbHeures;
    }
    
    public void setNbHeures(Integer nbHeures) {
        this.nbHeures = nbHeures;
    }
    
    public Boolean getArchive() {
        return archive;
    }
    
    public void setArchive(Boolean archive) {
        this.archive = archive;
    }
    
    public Classe getClasse() {
        return classe;
    }
    
    public void setClasse(Classe classe) {
        this.classe = classe;
    }
    
    @Override
    public String toString() {
        return "CoursClassroom{" +
                "idCours=" + idCours +
                ", specialite=" + specialite +
                ", nom='" + nom + '\'' +
                ", nbHeures=" + nbHeures +
                ", archive=" + archive +
                ", classeId=" + (classe != null ? classe.getCodeClasse() : null) +
                '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CoursClassroom that = (CoursClassroom) o;
        return idCours != null && idCours.equals(that.idCours);
    }
    
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
