package org.example.classroommanagement.config;

import org.example.classroommanagement.entities.Niveau;
import org.example.classroommanagement.entities.Specialite;
import org.example.classroommanagement.services.IClassroomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

/**
 * Unit tests for DataInitializer component.
 * Verifies that initial data is correctly populated on application startup.
 */
@ExtendWith(MockitoExtension.class)
class DataInitializerTest {
    
    @Mock
    private IClassroomService classroomService;
    
    @InjectMocks
    private DataInitializer dataInitializer;
    
    @BeforeEach
    void setUp() {
        // Mock service methods to return objects with IDs
        when(classroomService.ajouterUtilisateur(any())).thenAnswer(invocation -> {
            var user = invocation.getArgument(0, org.example.classroommanagement.entities.Utilisateur.class);
            user.setIdUtilisateur(1);
            return user;
        });
        
        when(classroomService.ajouterClasse(any())).thenAnswer(invocation -> {
            var classe = invocation.getArgument(0, org.example.classroommanagement.entities.Classe.class);
            classe.setCodeClasse(1);
            return classe;
        });
        
        when(classroomService.ajouterCoursClassroom(any(), anyInt())).thenAnswer(invocation -> {
            var cours = invocation.getArgument(0, org.example.classroommanagement.entities.CoursClassroom.class);
            cours.setIdCours(1);
            return cours;
        });
    }
    
    @Test
    void run_ShouldCreateTwoUsers() throws Exception {
        // When
        dataInitializer.run();
        
        // Then
        verify(classroomService, times(2)).ajouterUtilisateur(any());
    }
    
    @Test
    void run_ShouldCreateTwoClasses() throws Exception {
        // When
        dataInitializer.run();
        
        // Then
        verify(classroomService, times(2)).ajouterClasse(any());
    }
    
    @Test
    void run_ShouldCreateThreeCourses() throws Exception {
        // When
        dataInitializer.run();
        
        // Then
        verify(classroomService, times(3)).ajouterCoursClassroom(any(), anyInt());
    }
    
    @Test
    void run_ShouldAssignUsersToClasses() throws Exception {
        // When
        dataInitializer.run();
        
        // Then
        verify(classroomService, times(2)).affecterUtilisateurClasse(anyInt(), anyInt());
    }
    
    @Test
    void run_ShouldCreateUsersWithCorrectData() throws Exception {
        // When
        dataInitializer.run();
        
        // Then
        verify(classroomService).ajouterUtilisateur(argThat(user ->
            "Amna".equals(user.getPrenom()) &&
            "Ammar".equals(user.getNom()) &&
            "etudiant".equals(user.getPassword())
        ));
        
        verify(classroomService).ajouterUtilisateur(argThat(user ->
            "Ahmed".equals(user.getPrenom()) &&
            "Slama".equals(user.getNom()) &&
            "admin".equals(user.getPassword())
        ));
    }
    
    @Test
    void run_ShouldCreateClassesWithCorrectData() throws Exception {
        // When
        dataInitializer.run();
        
        // Then
        verify(classroomService).ajouterClasse(argThat(classe ->
            "4AG1".equals(classe.getTitre()) &&
            Niveau.QUATRIEME.equals(classe.getNiveau())
        ));
        
        verify(classroomService).ajouterClasse(argThat(classe ->
            "5EM1".equals(classe.getTitre()) &&
            Niveau.CINQUIEME.equals(classe.getNiveau())
        ));
    }
    
    @Test
    void run_ShouldCreateCoursesWithCorrectData() throws Exception {
        // When
        dataInitializer.run();
        
        // Then
        verify(classroomService).ajouterCoursClassroom(argThat(cours ->
            "Programmation C".equals(cours.getNom()) &&
            Specialite.INFORMATIQUE.equals(cours.getSpecialite()) &&
            42 == cours.getNbHeures()
        ), anyInt());
        
        verify(classroomService).ajouterCoursClassroom(argThat(cours ->
            "Plantes".equals(cours.getNom()) &&
            Specialite.AGRICULTURE.equals(cours.getSpecialite()) &&
            25 == cours.getNbHeures()
        ), anyInt());
        
        verify(classroomService).ajouterCoursClassroom(argThat(cours ->
            "Sciences Naturelles".equals(cours.getNom()) &&
            Specialite.AGRICULTURE.equals(cours.getSpecialite()) &&
            40 == cours.getNbHeures()
        ), anyInt());
    }
}
