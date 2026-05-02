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
 * Entity representing a course in the classroom management system.
 * Each course has a specialty, name, hours, and archive status.
 * It maintains a many-to-one relationship with Classe (one course belongs to one class).
 * 
 * Validates Requirements: 3.1, 3.2, 3.3, 3.4, 9.1, 9.4, 11.3, 11.5, 11.6, 11.7
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CoursClassroom implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Integer idCours;
    
    @Enumerated(EnumType.STRING)
    @ToString.Include
    private Specialite specialite;
    
    @ToString.Include
    private String nom;
    
    @ToString.Include
    private Integer nbHeures;
    
    @ToString.Include
    private Boolean archive = false;
    
    @ManyToOne
    @JsonBackReference("classe-cours")
    private Classe classe;
}
