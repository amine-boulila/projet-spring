package org.example.classroommanagement.services;

import org.example.classroommanagement.entities.Classe;
import org.example.classroommanagement.entities.CoursClassroom;
import org.example.classroommanagement.entities.Niveau;
import org.example.classroommanagement.entities.Specialite;
import org.example.classroommanagement.entities.Utilisateur;

/**
 * Service interface defining the contract for classroom management business operations.
 * This interface specifies all operations for managing users, classes, and courses
 * in the classroom management system.
 * 
 * Validates Requirements: 1.4, 2.5, 3.5, 4.1, 5.1, 6.1, 7.1, 8.1, 12.1
 */
public interface IClassroomService {
    
    /**
     * Adds a new user to the system.
     * 
     * @param utilisateur the user to add (must have prenom, nom, and password)
     * @return the persisted user with generated idUtilisateur
     * @throws IllegalArgumentException if prenom or nom is null or empty
     * 
     * Validates Requirement: 1.4
     */
    Utilisateur ajouterUtilisateur(Utilisateur utilisateur);
    
    /**
     * Adds a new class to the system.
     * 
     * @param c the class to add (must have titre and niveau)
     * @return the persisted class with generated codeClasse
     * @throws IllegalArgumentException if titre is null or empty, or niveau is invalid
     * 
     * Validates Requirement: 2.5
     */
    Classe ajouterClasse(Classe c);
    
    /**
     * Adds a new course to the system and associates it with a class.
     * 
     * @param cc the course to add (must have nom, nbHeures, and specialite)
     * @param codeClasse the ID of the class to associate the course with
     * @return the persisted course with generated idCours
     * @throws IllegalArgumentException if nom is null or empty, or nbHeures is not positive
     * @throws jakarta.persistence.EntityNotFoundException if codeClasse does not exist
     * 
     * Validates Requirement: 3.5
     */
    CoursClassroom ajouterCoursClassroom(CoursClassroom cc, Integer codeClasse);
    
    /**
     * Assigns a user to a class by updating the many-to-one relationship.
     * 
     * @param idUtilisateur the ID of the user to assign
     * @param codeClasse the ID of the class to assign the user to
     * @throws jakarta.persistence.EntityNotFoundException if idUtilisateur or codeClasse does not exist
     * 
     * Validates Requirement: 4.1
     */
    void affecterUtilisateurClasse(Integer idUtilisateur, Integer codeClasse);
    
    /**
     * Counts the number of users assigned to classes with the specified academic level.
     * 
     * @param nv the academic level to filter by
     * @return the count of users, or zero if no users exist for the specified level
     * @throws IllegalArgumentException if nv is null
     * 
     * Validates Requirement: 5.1
     */
    Integer nbUtilisateursParNiveau(Niveau nv);
    
    /**
     * Unassigns a course from its class by setting the classe relationship to null.
     * 
     * @param idCours the ID of the course to unassign
     * @throws jakarta.persistence.EntityNotFoundException if idCours does not exist
     * 
     * Validates Requirement: 6.1
     */
    void desaffecterCoursClassroomClasse(Integer idCours);
    
    /**
     * Archives all courses in the system by setting their archive attribute to true.
     * This method is scheduled to run every 60 seconds using Spring's @Scheduled annotation.
     * 
     * Validates Requirement: 7.1
     */
    void archiverCoursClassrooms();
    
    /**
     * Calculates the total number of hours for courses matching the specified specialty
     * and academic level.
     * 
     * @param sp the specialty to filter by
     * @param nv the academic level to filter by
     * @return the sum of nbHeures for matching courses, or zero if no courses match
     * @throws IllegalArgumentException if sp or nv is null
     * 
     * Validates Requirement: 8.1
     */
    Integer nbHeuresParSpecEtNiv(Specialite sp, Niveau nv);
    
    /**
     * Retrieves all users in the system.
     * 
     * @return a list of all users
     */
    java.util.List<Utilisateur> getAllUtilisateurs();
    
    /**
     * Retrieves a user by their ID.
     * 
     * @param idUtilisateur the ID of the user to retrieve
     * @return the user with the specified ID
     * @throws jakarta.persistence.EntityNotFoundException if the user does not exist
     */
    Utilisateur getUtilisateurById(Integer idUtilisateur);
}
