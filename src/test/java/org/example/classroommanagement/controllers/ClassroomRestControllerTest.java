package org.example.classroommanagement.controllers;

import jakarta.persistence.EntityNotFoundException;
import org.example.classroommanagement.entities.Classe;
import org.example.classroommanagement.entities.CoursClassroom;
import org.example.classroommanagement.entities.Niveau;
import org.example.classroommanagement.entities.Specialite;
import org.example.classroommanagement.entities.Utilisateur;
import org.example.classroommanagement.services.IClassroomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * Unit tests for ClassroomRestController.
 * Tests all 7 REST endpoints with mocked service layer.
 */
@ExtendWith(MockitoExtension.class)
class ClassroomRestControllerTest {
    
    @Mock
    private IClassroomService classroomService;
    
    @InjectMocks
    private ClassroomRestController controller;
    
    private Utilisateur testUser;
    private Classe testClasse;
    private CoursClassroom testCours;
    
    @BeforeEach
    void setUp() {
        testUser = new Utilisateur("John", "Doe", "password123");
        testUser.setIdUtilisateur(1);
        
        testClasse = new Classe("4INFO", Niveau.QUATRIEME);
        testClasse.setCodeClasse(1);
        
        testCours = new CoursClassroom(Specialite.INFORMATIQUE, "Java Programming", 40);
        testCours.setIdCours(1);
    }
    
    // Test Endpoint 1: POST /utilisateurs
    
    @Test
    void ajouterUtilisateur_shouldReturn201WithCreatedUser() {
        // Arrange
        when(classroomService.ajouterUtilisateur(any(Utilisateur.class))).thenReturn(testUser);
        
        // Act
        ResponseEntity<Utilisateur> response = controller.ajouterUtilisateur(testUser);
        
        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getIdUtilisateur()).isEqualTo(1);
        assertThat(response.getBody().getPrenom()).isEqualTo("John");
        verify(classroomService, times(1)).ajouterUtilisateur(any(Utilisateur.class));
    }
    
    // Test Endpoint 2: POST /classes
    
    @Test
    void ajouterClasse_shouldReturn201WithCreatedClasse() {
        // Arrange
        when(classroomService.ajouterClasse(any(Classe.class))).thenReturn(testClasse);
        
        // Act
        ResponseEntity<Classe> response = controller.ajouterClasse(testClasse);
        
        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getCodeClasse()).isEqualTo(1);
        assertThat(response.getBody().getTitre()).isEqualTo("4INFO");
        verify(classroomService, times(1)).ajouterClasse(any(Classe.class));
    }
    
    // Test Endpoint 3: POST /cours/{codeClasse}
    
    @Test
    void ajouterCoursClassroom_shouldReturn201WithCreatedCours_whenClasseExists() {
        // Arrange
        when(classroomService.ajouterCoursClassroom(any(CoursClassroom.class), eq(1)))
                .thenReturn(testCours);
        
        // Act
        ResponseEntity<CoursClassroom> response = controller.ajouterCoursClassroom(testCours, 1);
        
        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getIdCours()).isEqualTo(1);
        assertThat(response.getBody().getNom()).isEqualTo("Java Programming");
        verify(classroomService, times(1)).ajouterCoursClassroom(any(CoursClassroom.class), eq(1));
    }
    
    @Test
    void ajouterCoursClassroom_shouldReturn404_whenClasseNotFound() {
        // Arrange
        when(classroomService.ajouterCoursClassroom(any(CoursClassroom.class), eq(999)))
                .thenThrow(new EntityNotFoundException("Classe not found"));
        
        // Act
        ResponseEntity<CoursClassroom> response = controller.ajouterCoursClassroom(testCours, 999);
        
        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNull();
        verify(classroomService, times(1)).ajouterCoursClassroom(any(CoursClassroom.class), eq(999));
    }
    
    // Test Endpoint 4: PUT /utilisateurs/{idUtilisateur}/classes/{codeClasse}
    
    @Test
    void affecterUtilisateurClasse_shouldReturn204_whenBothExist() {
        // Arrange
        doNothing().when(classroomService).affecterUtilisateurClasse(1, 1);
        
        // Act
        ResponseEntity<Void> response = controller.affecterUtilisateurClasse(1, 1);
        
        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(response.getBody()).isNull();
        verify(classroomService, times(1)).affecterUtilisateurClasse(1, 1);
    }
    
    @Test
    void affecterUtilisateurClasse_shouldReturn404_whenUserNotFound() {
        // Arrange
        doThrow(new EntityNotFoundException("User not found"))
                .when(classroomService).affecterUtilisateurClasse(999, 1);
        
        // Act
        ResponseEntity<Void> response = controller.affecterUtilisateurClasse(999, 1);
        
        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        verify(classroomService, times(1)).affecterUtilisateurClasse(999, 1);
    }
    
    @Test
    void affecterUtilisateurClasse_shouldReturn404_whenClasseNotFound() {
        // Arrange
        doThrow(new EntityNotFoundException("Classe not found"))
                .when(classroomService).affecterUtilisateurClasse(1, 999);
        
        // Act
        ResponseEntity<Void> response = controller.affecterUtilisateurClasse(1, 999);
        
        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        verify(classroomService, times(1)).affecterUtilisateurClasse(1, 999);
    }
    
    // Test Endpoint 5: GET /utilisateurs/count?niveau=X
    
    @Test
    void nbUtilisateursParNiveau_shouldReturn200WithCount() {
        // Arrange
        when(classroomService.nbUtilisateursParNiveau(Niveau.QUATRIEME)).thenReturn(5);
        
        // Act
        ResponseEntity<Integer> response = controller.nbUtilisateursParNiveau(Niveau.QUATRIEME);
        
        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(5);
        verify(classroomService, times(1)).nbUtilisateursParNiveau(Niveau.QUATRIEME);
    }
    
    @Test
    void nbUtilisateursParNiveau_shouldReturn200WithZero_whenNoUsers() {
        // Arrange
        when(classroomService.nbUtilisateursParNiveau(Niveau.PREMIERE)).thenReturn(0);
        
        // Act
        ResponseEntity<Integer> response = controller.nbUtilisateursParNiveau(Niveau.PREMIERE);
        
        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(0);
        verify(classroomService, times(1)).nbUtilisateursParNiveau(Niveau.PREMIERE);
    }
    
    // Test Endpoint 6: DELETE /cours/{idCours}/classe
    
    @Test
    void desaffecterCoursClassroomClasse_shouldReturn204_whenCoursExists() {
        // Arrange
        doNothing().when(classroomService).desaffecterCoursClassroomClasse(1);
        
        // Act
        ResponseEntity<Void> response = controller.desaffecterCoursClassroomClasse(1);
        
        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(response.getBody()).isNull();
        verify(classroomService, times(1)).desaffecterCoursClassroomClasse(1);
    }
    
    @Test
    void desaffecterCoursClassroomClasse_shouldReturn404_whenCoursNotFound() {
        // Arrange
        doThrow(new EntityNotFoundException("Course not found"))
                .when(classroomService).desaffecterCoursClassroomClasse(999);
        
        // Act
        ResponseEntity<Void> response = controller.desaffecterCoursClassroomClasse(999);
        
        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        verify(classroomService, times(1)).desaffecterCoursClassroomClasse(999);
    }
    
    // Test Endpoint 7: GET /cours/heures?specialite=X&niveau=Y
    
    @Test
    void nbHeuresParSpecEtNiv_shouldReturn200WithTotalHours() {
        // Arrange
        when(classroomService.nbHeuresParSpecEtNiv(Specialite.INFORMATIQUE, Niveau.QUATRIEME))
                .thenReturn(120);
        
        // Act
        ResponseEntity<Integer> response = controller.nbHeuresParSpecEtNiv(
                Specialite.INFORMATIQUE, Niveau.QUATRIEME);
        
        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(120);
        verify(classroomService, times(1))
                .nbHeuresParSpecEtNiv(Specialite.INFORMATIQUE, Niveau.QUATRIEME);
    }
    
    @Test
    void nbHeuresParSpecEtNiv_shouldReturn200WithZero_whenNoCourses() {
        // Arrange
        when(classroomService.nbHeuresParSpecEtNiv(Specialite.AGRICULTURE, Niveau.PREMIERE))
                .thenReturn(0);
        
        // Act
        ResponseEntity<Integer> response = controller.nbHeuresParSpecEtNiv(
                Specialite.AGRICULTURE, Niveau.PREMIERE);
        
        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(0);
        verify(classroomService, times(1))
                .nbHeuresParSpecEtNiv(Specialite.AGRICULTURE, Niveau.PREMIERE);
    }
}
