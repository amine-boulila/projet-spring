package org.example.classroommanagement.repositories;

import org.example.classroommanagement.entities.Niveau;
import org.example.classroommanagement.entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for Utilisateur entity.
 * Provides CRUD operations and custom query methods for user data access.
 * 
 * Validates Requirements: 5.2, 12.4
 */
@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Integer> {
    
    /**
     * Find all users whose class has the specified academic level.
     * Uses Spring Data JPA property expression to navigate the relationship:
     * classe.niveau
     * 
     * @param niveau the academic level to filter by
     * @return list of users in classes with the specified level
     */
    List<Utilisateur> findByClasse_Niveau(Niveau niveau);
}
