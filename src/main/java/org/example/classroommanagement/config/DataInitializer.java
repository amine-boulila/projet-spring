package org.example.classroommanagement.config;

import org.example.classroommanagement.entities.*;
import org.example.classroommanagement.repositories.ClasseRepository;
import org.example.classroommanagement.repositories.CoursClassroomRepository;
import org.example.classroommanagement.repositories.UtilisateurRepository;
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
    private final UtilisateurRepository utilisateurRepository;
    private final ClasseRepository classeRepository;
    private final CoursClassroomRepository coursClassroomRepository;
    
    /**
     * Constructor with direct repository access for idempotent startup seeding.
     *
     * @param classroomService the classroom service for data operations
     * @param utilisateurRepository repository used to check whether users already exist
     * @param classeRepository repository used to check whether classes already exist
     * @param coursClassroomRepository repository used to check whether courses already exist
     */
    public DataInitializer(
            IClassroomService classroomService,
            UtilisateurRepository utilisateurRepository,
            ClasseRepository classeRepository,
            CoursClassroomRepository coursClassroomRepository) {
        this.classroomService = classroomService;
        this.utilisateurRepository = utilisateurRepository;
        this.classeRepository = classeRepository;
        this.coursClassroomRepository = coursClassroomRepository;
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
        if (utilisateurRepository.count() > 0 || classeRepository.count() > 0 || coursClassroomRepository.count() > 0) {
            log.info("Skipping demo data initialization because the database already contains data.");
            return;
        }

        log.info("Starting data initialization...");
        
        // Create and add users
        Utilisateur amna = new Utilisateur();
        amna.setPrenom("Amna");
        amna.setNom("Ammar");
        amna.setPassword("etudiant");

        Utilisateur ahmed = new Utilisateur();
        ahmed.setPrenom("Ahmed");
        ahmed.setNom("Slama");
        ahmed.setPassword("admin");
        
        amna = classroomService.ajouterUtilisateur(amna);
        ahmed = classroomService.ajouterUtilisateur(ahmed);
        log.info("Created users: {} and {}", amna.getPrenom() + " " + amna.getNom(), 
                 ahmed.getPrenom() + " " + ahmed.getNom());
        
        // Create and add classes
        Classe classe4AG1 = new Classe();
        classe4AG1.setTitre("4AG1");
        classe4AG1.setNiveau(Niveau.QUATRIEME);

        Classe classe5EM1 = new Classe();
        classe5EM1.setTitre("5EM1");
        classe5EM1.setNiveau(Niveau.CINQUIEME);
        
        classe4AG1 = classroomService.ajouterClasse(classe4AG1);
        classe5EM1 = classroomService.ajouterClasse(classe5EM1);
        log.info("Created classes: {} and {}", classe4AG1.getTitre(), classe5EM1.getTitre());
        
        // Create and add courses
        CoursClassroom programmationC = new CoursClassroom();
        programmationC.setSpecialite(Specialite.INFORMATIQUE);
        programmationC.setNom("Programmation C");
        programmationC.setNbHeures(42);

        CoursClassroom plantes = new CoursClassroom();
        plantes.setSpecialite(Specialite.AGRICULTURE);
        plantes.setNom("Plantes");
        plantes.setNbHeures(25);

        CoursClassroom sciencesNaturelles = new CoursClassroom();
        sciencesNaturelles.setSpecialite(Specialite.AGRICULTURE);
        sciencesNaturelles.setNom("Sciences Naturelles");
        sciencesNaturelles.setNbHeures(40);
        
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
