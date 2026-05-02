package org.example.classroommanagement.repositories;

import org.example.classroommanagement.entities.Classe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for Classe entity.
 * Provides CRUD operations for class data access.
 * 
 * Validates Requirements: 12.4
 */
@Repository
public interface ClasseRepository extends JpaRepository<Classe, Integer> {
    // Custom query methods can be added here if needed
}
