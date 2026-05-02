package org.example.classroommanagement.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import jakarta.persistence.*;
import java.io.Serializable;

/**
 * Entity representing a user (student or administrator) in the classroom management system.
 * Each user can be assigned to one class (many-to-one relationship with Classe).
 * 
 * Validates Requirements: 1.1, 1.2, 1.3, 11.1, 11.5, 11.6
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Utilisateur implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Integer idUtilisateur;
    
    @ToString.Include
    private String prenom;
    
    @ToString.Include
    private String nom;
    
    private String password;
    
    @ManyToOne
    @JsonBackReference("classe-utilisateurs")
    private Classe classe;
}
