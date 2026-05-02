package org.example.classroommanagement.repositories;

import org.example.classroommanagement.entities.Classe;
import org.example.classroommanagement.entities.Niveau;
import org.example.classroommanagement.entities.Utilisateur;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for UtilisateurRepository.
 * Tests the custom query method findByClasse_Niveau.
 * 
 * Validates Requirements: 5.2, 12.4
 */
@DataJpaTest
class UtilisateurRepositoryTest {
    
    @Autowired
    private TestEntityManager entityManager;
    
    @Autowired
    private UtilisateurRepository utilisateurRepository;
    
    @Test
    void should_FindUsersByClasseNiveau_When_UsersExistForLevel() {
        // Arrange
        Classe classe1 = new Classe("4INFO", Niveau.QUATRIEME);
        entityManager.persist(classe1);
        
        Classe classe2 = new Classe("3INFO", Niveau.TROISIEME);
        entityManager.persist(classe2);
        
        Utilisateur user1 = new Utilisateur("Alice", "Dupont", "pass123", classe1);
        entityManager.persist(user1);
        
        Utilisateur user2 = new Utilisateur("Bob", "Martin", "pass456", classe1);
        entityManager.persist(user2);
        
        Utilisateur user3 = new Utilisateur("Charlie", "Bernard", "pass789", classe2);
        entityManager.persist(user3);
        
        entityManager.flush();
        
        // Act
        List<Utilisateur> quatriemeUsers = utilisateurRepository.findByClasse_Niveau(Niveau.QUATRIEME);
        List<Utilisateur> troisiemeUsers = utilisateurRepository.findByClasse_Niveau(Niveau.TROISIEME);
        
        // Assert
        assertThat(quatriemeUsers).hasSize(2);
        assertThat(quatriemeUsers).extracting(Utilisateur::getPrenom)
            .containsExactlyInAnyOrder("Alice", "Bob");
        
        assertThat(troisiemeUsers).hasSize(1);
        assertThat(troisiemeUsers).extracting(Utilisateur::getPrenom)
            .containsExactly("Charlie");
    }
    
    @Test
    void should_ReturnEmptyList_When_NoUsersExistForLevel() {
        // Arrange
        Classe classe = new Classe("4INFO", Niveau.QUATRIEME);
        entityManager.persist(classe);
        
        Utilisateur user = new Utilisateur("Alice", "Dupont", "pass123", classe);
        entityManager.persist(user);
        
        entityManager.flush();
        
        // Act
        List<Utilisateur> premiereUsers = utilisateurRepository.findByClasse_Niveau(Niveau.PREMIERE);
        
        // Assert
        assertThat(premiereUsers).isEmpty();
    }
    
    @Test
    void should_NotIncludeUsersWithoutClasse_When_FindingByNiveau() {
        // Arrange
        Classe classe = new Classe("4INFO", Niveau.QUATRIEME);
        entityManager.persist(classe);
        
        Utilisateur userWithClasse = new Utilisateur("Alice", "Dupont", "pass123", classe);
        entityManager.persist(userWithClasse);
        
        Utilisateur userWithoutClasse = new Utilisateur("Bob", "Martin", "pass456");
        entityManager.persist(userWithoutClasse);
        
        entityManager.flush();
        
        // Act
        List<Utilisateur> quatriemeUsers = utilisateurRepository.findByClasse_Niveau(Niveau.QUATRIEME);
        
        // Assert
        assertThat(quatriemeUsers).hasSize(1);
        assertThat(quatriemeUsers.get(0).getPrenom()).isEqualTo("Alice");
    }
}
