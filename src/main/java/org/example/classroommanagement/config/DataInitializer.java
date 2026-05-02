package org.example.classroommanagement.config;

import org.example.classroommanagement.entities.*;
import org.example.classroommanagement.services.IClassroomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Component that initializes the database with demonstration data on application startup.
 * Implements CommandLineRunner to execute after the Spring context is fully initialized.
 * 
 * Validates Requirements: 10.1, 10.2
 */
@Component
public class DataInitializer implements CommandLineRunner {
    
    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);
    
    private final IClassroomService classroomService;
    
    /**
     * Constructor with dependency injection.
     * 
     * @param classroomService the classroom service for data operations
     */
    public DataInitializer(IClassroomService classroomService) {
        this.classroomService = classroomService;
    }
    
    /**
     * Executes on application startup to populate initial demonstration data.
     * Creates users, classes, courses, and establishes relationships.
     * 
     * @param args command line arguments (not used)
     * @throws Exception if initialization fails
     */
    @Override
    public void run(String... args) throws Exception {
        log.info("Starting data initialization...");
        
        // Create and add users
        Utilisateur amna = new Utilisateur("Amna", "Ammar", "etudiant");
        Utilisateur ahmed = new Utilisateur("Ahmed", "Slama", "admin");
        
        amna = classroomService.ajouterUtilisateur(amna);
        ahmed = classroomService.ajouterUtilisateur(ahmed);
        log.info("Created users: {} and {}", amna.getPrenom() + " " + amna.getNom(), 
                 ahmed.getPrenom() + " " + ahmed.getNom());
        
        // Create and add classes
        Classe classe4AG1 = new Classe("4AG1", Niveau.QUATRIEME);
        Classe classe5EM1 = new Classe("5EM1", Niveau.CINQUIEME);
        
        classe4AG1 = classroomService.ajouterClasse(classe4AG1);
        classe5EM1 = classroomService.ajouterClasse(classe5EM1);
        log.info("Created classes: {} and {}", classe4AG1.getTitre(), classe5EM1.getTitre());
        
        // Create and add courses
        CoursClassroom programmationC = new CoursClassroom(Specialite.INFORMATIQUE, "Programmation C", 42);
        CoursClassroom plantes = new CoursClassroom(Specialite.AGRICULTURE, "Plantes", 25);
        CoursClassroom sciencesNaturelles = new CoursClassroom(Specialite.AGRICULTURE, "Sciences Naturelles", 40);
        
        programmationC = classroomService.ajouterCoursClassroom(programmationC, classe4AG1.getCodeClasse());
        plantes = classroomService.ajouterCoursClassroom(plantes, classe4AG1.getCodeClasse());
        sciencesNaturelles = classroomService.ajouterCoursClassroom(sciencesNaturelles, classe4AG1.getCodeClasse());
        log.info("Created courses: {}, {}, {}", programmationC.getNom(), plantes.getNom(), 
                 sciencesNaturelles.getNom());
        
        // Assign users to classes
        classroomService.affecterUtilisateurClasse(amna.getIdUtilisateur(), classe4AG1.getCodeClasse());
        classroomService.affecterUtilisateurClasse(ahmed.getIdUtilisateur(), classe5EM1.getCodeClasse());
        log.info("Assigned {} to {} and {} to {}", 
                 amna.getPrenom() + " " + amna.getNom(), classe4AG1.getTitre(),
                 ahmed.getPrenom() + " " + ahmed.getNom(), classe5EM1.getTitre());
        
        log.info("Data initialization completed successfully!");
    }
}
