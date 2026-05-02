package org.example.classroommanagement.repositories;

import org.example.classroommanagement.entities.CoursClassroom;
import org.example.classroommanagement.entities.Niveau;
import org.example.classroommanagement.entities.Specialite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for CoursClassroom entity.
 * Provides CRUD operations and custom query methods for course data access.
 * 
 * Validates Requirements: 8.2, 8.3, 12.4
 */
@Repository
public interface CoursClassroomRepository extends JpaRepository<CoursClassroom, Integer> {
    
    /**
     * Find all courses with the specified specialty whose class has the specified academic level.
     * Uses Spring Data JPA property expression to navigate the relationship:
     * specialite AND classe.niveau
     * 
     * This method is used to calculate total hours by specialty and academic level.
     * 
     * @param specialite the specialty to filter by
     * @param niveau the academic level to filter by
     * @return list of courses matching the specialty and level criteria
     */
    List<CoursClassroom> findBySpecialiteAndClasse_Niveau(Specialite specialite, Niveau niveau);
}
