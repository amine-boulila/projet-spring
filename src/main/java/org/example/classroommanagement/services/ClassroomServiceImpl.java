package org.example.classroommanagement.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.example.classroommanagement.entities.Classe;
import org.example.classroommanagement.entities.CoursClassroom;
import org.example.classroommanagement.entities.Niveau;
import org.example.classroommanagement.entities.Specialite;
import org.example.classroommanagement.entities.Utilisateur;
import org.example.classroommanagement.repositories.ClasseRepository;
import org.example.classroommanagement.repositories.CoursClassroomRepository;
import org.example.classroommanagement.repositories.UtilisateurRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation of IClassroomService providing business logic for classroom management operations.
 * This service handles all CRUD operations and business rules for users, classes, and courses.
 * 
 * All methods are transactional to ensure data consistency.
 * 
 * Validates Requirements: 1.4, 1.5, 1.6, 1.7, 2.5, 2.6, 2.7, 2.8, 3.5, 3.6, 3.7, 3.8, 3.9, 3.10,
 *                         4.1, 4.2, 4.3, 4.4, 4.5, 4.6, 5.1, 5.2, 5.3, 5.4, 5.5, 6.1, 6.2, 6.3,
 *                         6.4, 6.5, 7.1, 7.2, 7.3, 7.4, 7.5, 8.1, 8.2, 8.3, 8.4, 8.5, 8.6, 8.7,
 *                         9.2, 12.2, 12.3, 12.4, 12.5
 */
@Service
@Transactional
@Slf4j
public class ClassroomServiceImpl implements IClassroomService {
    
    private final UtilisateurRepository utilisateurRepository;
    private final ClasseRepository classeRepository;
    private final CoursClassroomRepository coursClassroomRepository;
    
    /**
     * Constructor with dependency injection for all required repositories.
     * 
     * @param utilisateurRepository repository for user data access
     * @param classeRepository repository for class data access
     * @param coursClassroomRepository repository for course data access
     */
    public ClassroomServiceImpl(
            UtilisateurRepository utilisateurRepository,
            ClasseRepository classeRepository,
            CoursClassroomRepository coursClassroomRepository) {
        this.utilisateurRepository = utilisateurRepository;
        this.classeRepository = classeRepository;
        this.coursClassroomRepository = coursClassroomRepository;
    }
    
    /**
     * Adds a new user to the system with validation.
     * 
     * @param utilisateur the user to add
     * @return the persisted user with generated ID
     * @throws IllegalArgumentException if prenom or nom is null or empty
     * 
     * Validates Requirements: 1.4, 1.5, 1.6, 1.7
     */
    @Override
    public Utilisateur ajouterUtilisateur(Utilisateur utilisateur) {
        log.debug("Adding new user: {}", utilisateur);
        
        // Validate prenom
        if (utilisateur.getPrenom() == null || utilisateur.getPrenom().trim().isEmpty()) {
            log.error("Validation failed: prenom is null or empty");
            throw new IllegalArgumentException("Prenom must not be null or empty");
        }
        
        // Validate nom
        if (utilisateur.getNom() == null || utilisateur.getNom().trim().isEmpty()) {
            log.error("Validation failed: nom is null or empty");
            throw new IllegalArgumentException("Nom must not be null or empty");
        }
        
        Utilisateur savedUser = utilisateurRepository.save(utilisateur);
        log.info("User added successfully with ID: {}", savedUser.getIdUtilisateur());
        return savedUser;
    }
    
    /**
     * Adds a new class to the system with validation.
     * 
     * @param c the class to add
     * @return the persisted class with generated ID
     * @throws IllegalArgumentException if titre is null or empty, or niveau is null
     * 
     * Validates Requirements: 2.5, 2.6, 2.7, 2.8
     */
    @Override
    public Classe ajouterClasse(Classe c) {
        log.debug("Adding new class: {}", c);
        
        // Validate titre
        if (c.getTitre() == null || c.getTitre().trim().isEmpty()) {
            log.error("Validation failed: titre is null or empty");
            throw new IllegalArgumentException("Titre must not be null or empty");
        }
        
        // Validate niveau
        if (c.getNiveau() == null) {
            log.error("Validation failed: niveau is null");
            throw new IllegalArgumentException("Niveau must not be null");
        }
        
        Classe savedClasse = classeRepository.save(c);
        log.info("Class added successfully with code: {}", savedClasse.getCodeClasse());
        return savedClasse;
    }
    
    /**
     * Adds a new course to the system and associates it with a class.
     * Maintains bidirectional relationship between course and class.
     * 
     * @param cc the course to add
     * @param codeClasse the ID of the class to associate with
     * @return the persisted course with generated ID
     * @throws IllegalArgumentException if nom is null or empty, or nbHeures is not positive
     * @throws EntityNotFoundException if codeClasse does not exist
     * 
     * Validates Requirements: 3.5, 3.6, 3.7, 3.8, 3.9, 3.10
     */
    @Override
    public CoursClassroom ajouterCoursClassroom(CoursClassroom cc, Integer codeClasse) {
        log.debug("Adding new course: {} to class: {}", cc, codeClasse);
        
        // Validate nom
        if (cc.getNom() == null || cc.getNom().trim().isEmpty()) {
            log.error("Validation failed: nom is null or empty");
            throw new IllegalArgumentException("Nom must not be null or empty");
        }
        
        // Validate nbHeures
        if (cc.getNbHeures() == null || cc.getNbHeures() <= 0) {
            log.error("Validation failed: nbHeures is null or not positive");
            throw new IllegalArgumentException("NbHeures must be positive");
        }
        
        // Find the class
        Classe classe = classeRepository.findById(codeClasse)
                .orElseThrow(() -> {
                    log.error("Class not found with code: {}", codeClasse);
                    return new EntityNotFoundException("Classe not found with code: " + codeClasse);
                });
        
        // Set bidirectional relationship
        cc.setClasse(classe);
        classe.getCoursClassrooms().add(cc);
        
        CoursClassroom savedCours = coursClassroomRepository.save(cc);
        log.info("Course added successfully with ID: {} to class: {}", 
                savedCours.getIdCours(), codeClasse);
        return savedCours;
    }
    
    /**
     * Assigns a user to a class by updating the many-to-one relationship.
     * 
     * @param idUtilisateur the ID of the user to assign
     * @param codeClasse the ID of the class to assign to
     * @throws EntityNotFoundException if user or class does not exist
     * 
     * Validates Requirements: 4.1, 4.2, 4.3, 4.4, 4.5, 4.6
     */
    @Override
    public void affecterUtilisateurClasse(Integer idUtilisateur, Integer codeClasse) {
        log.debug("Assigning user {} to class {}", idUtilisateur, codeClasse);
        
        // Find the user
        Utilisateur utilisateur = utilisateurRepository.findById(idUtilisateur)
                .orElseThrow(() -> {
                    log.error("User not found with ID: {}", idUtilisateur);
                    return new EntityNotFoundException("Utilisateur not found with ID: " + idUtilisateur);
                });
        
        // Find the class
        Classe classe = classeRepository.findById(codeClasse)
                .orElseThrow(() -> {
                    log.error("Class not found with code: {}", codeClasse);
                    return new EntityNotFoundException("Classe not found with code: " + codeClasse);
                });
        
        // Update relationship
        utilisateur.setClasse(classe);
        classe.getUtilisateurs().add(utilisateur);
        
        utilisateurRepository.save(utilisateur);
        log.info("User {} assigned to class {} successfully", idUtilisateur, codeClasse);
    }
    
    /**
     * Counts the number of users assigned to classes with the specified academic level.
     * 
     * @param nv the academic level to filter by
     * @return the count of users
     * @throws IllegalArgumentException if nv is null
     * 
     * Validates Requirements: 5.1, 5.2, 5.3, 5.4, 5.5
     */
    @Override
    public Integer nbUtilisateursParNiveau(Niveau nv) {
        log.debug("Counting users for niveau: {}", nv);
        
        // Validate niveau
        if (nv == null) {
            log.error("Validation failed: niveau is null");
            throw new IllegalArgumentException("Niveau must not be null");
        }
        
        List<Utilisateur> utilisateurs = utilisateurRepository.findByClasse_Niveau(nv);
        int count = utilisateurs.size();
        log.info("Found {} users for niveau: {}", count, nv);
        return count;
    }
    
    /**
     * Unassigns a course from its class by setting the classe relationship to null.
     * 
     * @param idCours the ID of the course to unassign
     * @throws EntityNotFoundException if course does not exist
     * 
     * Validates Requirements: 6.1, 6.2, 6.3, 6.4, 6.5
     */
    @Override
    public void desaffecterCoursClassroomClasse(Integer idCours) {
        log.debug("Unassigning course {} from its class", idCours);
        
        // Find the course
        CoursClassroom cours = coursClassroomRepository.findById(idCours)
                .orElseThrow(() -> {
                    log.error("Course not found with ID: {}", idCours);
                    return new EntityNotFoundException("CoursClassroom not found with ID: " + idCours);
                });
        
        // Remove from class's collection if assigned
        if (cours.getClasse() != null) {
            cours.getClasse().getCoursClassrooms().remove(cours);
        }
        
        // Set classe to null
        cours.setClasse(null);
        
        coursClassroomRepository.save(cours);
        log.info("Course {} unassigned from its class successfully", idCours);
    }
    
    /**
     * Archives all courses in the system by setting their archive attribute to true.
     * This method is scheduled to run every 60 seconds (60000 milliseconds).
     * 
     * Validates Requirements: 7.1, 7.2, 7.3, 7.4, 7.5
     */
    @Scheduled(fixedRate = 60000)
    @Override
    public void archiverCoursClassrooms() {
        log.debug("Starting scheduled archiving of all courses");
        
        // Find all courses
        List<CoursClassroom> allCours = coursClassroomRepository.findAll();
        
        // Set archive to true for all courses
        for (CoursClassroom cours : allCours) {
            cours.setArchive(true);
        }
        
        // Save all courses
        coursClassroomRepository.saveAll(allCours);
        
        log.info("Archived {} courses successfully", allCours.size());
    }
    
    /**
     * Calculates the total number of hours for courses matching the specified specialty
     * and academic level.
     * 
     * @param sp the specialty to filter by
     * @param nv the academic level to filter by
     * @return the sum of nbHeures for matching courses
     * @throws IllegalArgumentException if sp or nv is null
     * 
     * Validates Requirements: 8.1, 8.2, 8.3, 8.4, 8.5, 8.6, 8.7
     */
    @Override
    public Integer nbHeuresParSpecEtNiv(Specialite sp, Niveau nv) {
        log.debug("Calculating total hours for specialite: {} and niveau: {}", sp, nv);
        
        // Validate specialite
        if (sp == null) {
            log.error("Validation failed: specialite is null");
            throw new IllegalArgumentException("Specialite must not be null");
        }
        
        // Validate niveau
        if (nv == null) {
            log.error("Validation failed: niveau is null");
            throw new IllegalArgumentException("Niveau must not be null");
        }
        
        // Query courses by specialite and niveau
        List<CoursClassroom> cours = coursClassroomRepository.findBySpecialiteAndClasse_Niveau(sp, nv);
        
        // Sum nbHeures
        int totalHeures = cours.stream()
                .mapToInt(CoursClassroom::getNbHeures)
                .sum();
        
        log.info("Total hours for specialite {} and niveau {}: {}", sp, nv, totalHeures);
        return totalHeures;
    }
    
    /**
     * Retrieves all users in the system.
     * 
     * @return a list of all users
     */
    @Override
    public List<Utilisateur> getAllUtilisateurs() {
        log.debug("Retrieving all users");
        List<Utilisateur> utilisateurs = utilisateurRepository.findAll();
        log.info("Found {} users", utilisateurs.size());
        return utilisateurs;
    }
    
    /**
     * Retrieves a user by their ID.
     * 
     * @param idUtilisateur the ID of the user to retrieve
     * @return the user with the specified ID
     * @throws EntityNotFoundException if the user does not exist
     */
    @Override
    public Utilisateur getUtilisateurById(Integer idUtilisateur) {
        log.debug("Retrieving user with ID: {}", idUtilisateur);
        
        Utilisateur utilisateur = utilisateurRepository.findById(idUtilisateur)
                .orElseThrow(() -> {
                    log.error("User not found with ID: {}", idUtilisateur);
                    return new EntityNotFoundException("Utilisateur not found with ID: " + idUtilisateur);
                });
        
        log.info("User found: {} {}", utilisateur.getPrenom(), utilisateur.getNom());
        return utilisateur;
    }
}
