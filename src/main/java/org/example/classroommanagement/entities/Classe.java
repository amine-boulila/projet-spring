package org.example.classroommanagement.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
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
@Getter
@Setter
@NoArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Classe implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Integer codeClasse;
    
    @ToString.Include
    private String titre;
    
    @Enumerated(EnumType.STRING)
    @ToString.Include
    private Niveau niveau;
    
    @OneToMany(mappedBy = "classe", cascade = CascadeType.ALL)
    @JsonManagedReference("classe-cours")
    private Set<CoursClassroom> coursClassrooms = new HashSet<>();
    
    @OneToMany(mappedBy = "classe")
    @JsonManagedReference("classe-utilisateurs")
    private Set<Utilisateur> utilisateurs = new HashSet<>();
}
