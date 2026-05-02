package org.example.classroommanagement.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Classe entity.
 * Tests entity creation, getters/setters, and bidirectional relationship helper methods.
 */
class ClasseTest {
    
    private Classe classe;
    
    @BeforeEach
    void setUp() {
        classe = new Classe("4INFO", Niveau.QUATRIEME);
    }
    
    @Test
    void testDefaultConstructor() {
        Classe emptyClasse = new Classe();
        assertNotNull(emptyClasse);
        assertNull(emptyClasse.getCodeClasse());
        assertNull(emptyClasse.getTitre());
        assertNull(emptyClasse.getNiveau());
        assertNotNull(emptyClasse.getCoursClassrooms());
        assertNotNull(emptyClasse.getUtilisateurs());
        assertTrue(emptyClasse.getCoursClassrooms().isEmpty());
        assertTrue(emptyClasse.getUtilisateurs().isEmpty());
    }
    
    @Test
    void testParameterizedConstructor() {
        assertEquals("4INFO", classe.getTitre());
        assertEquals(Niveau.QUATRIEME, classe.getNiveau());
        assertNotNull(classe.getCoursClassrooms());
        assertNotNull(classe.getUtilisateurs());
        assertTrue(classe.getCoursClassrooms().isEmpty());
        assertTrue(classe.getUtilisateurs().isEmpty());
    }
    
    @Test
    void testGettersAndSetters() {
        classe.setCodeClasse(1);
        classe.setTitre("5INFO");
        classe.setNiveau(Niveau.CINQUIEME);
        
        assertEquals(1, classe.getCodeClasse());
        assertEquals("5INFO", classe.getTitre());
        assertEquals(Niveau.CINQUIEME, classe.getNiveau());
    }
    
    @Test
    void testAddCoursClassroom_maintainsBidirectionalRelationship() {
        CoursClassroom cours = new CoursClassroom(Specialite.INFORMATIQUE, "Java Programming", 40);
        
        classe.addCoursClassroom(cours);
        
        assertTrue(classe.getCoursClassrooms().contains(cours));
        assertEquals(classe, cours.getClasse());
        assertEquals(1, classe.getCoursClassrooms().size());
    }
    
    @Test
    void testRemoveCoursClassroom_maintainsBidirectionalRelationship() {
        CoursClassroom cours = new CoursClassroom(Specialite.INFORMATIQUE, "Java Programming", 40);
        classe.addCoursClassroom(cours);
        
        classe.removeCoursClassroom(cours);
        
        assertFalse(classe.getCoursClassrooms().contains(cours));
        assertNull(cours.getClasse());
        assertEquals(0, classe.getCoursClassrooms().size());
    }
    
    @Test
    void testAddUtilisateur_maintainsBidirectionalRelationship() {
        Utilisateur utilisateur = new Utilisateur("John", "Doe", "password123");
        
        classe.addUtilisateur(utilisateur);
        
        assertTrue(classe.getUtilisateurs().contains(utilisateur));
        assertEquals(classe, utilisateur.getClasse());
        assertEquals(1, classe.getUtilisateurs().size());
    }
    
    @Test
    void testRemoveUtilisateur_maintainsBidirectionalRelationship() {
        Utilisateur utilisateur = new Utilisateur("John", "Doe", "password123");
        classe.addUtilisateur(utilisateur);
        
        classe.removeUtilisateur(utilisateur);
        
        assertFalse(classe.getUtilisateurs().contains(utilisateur));
        assertNull(utilisateur.getClasse());
        assertEquals(0, classe.getUtilisateurs().size());
    }
    
    @Test
    void testAddMultipleCoursClassrooms() {
        CoursClassroom cours1 = new CoursClassroom(Specialite.INFORMATIQUE, "Java", 40);
        CoursClassroom cours2 = new CoursClassroom(Specialite.INFORMATIQUE, "Python", 30);
        CoursClassroom cours3 = new CoursClassroom(Specialite.INFORMATIQUE, "Database", 35);
        
        classe.addCoursClassroom(cours1);
        classe.addCoursClassroom(cours2);
        classe.addCoursClassroom(cours3);
        
        assertEquals(3, classe.getCoursClassrooms().size());
        assertTrue(classe.getCoursClassrooms().contains(cours1));
        assertTrue(classe.getCoursClassrooms().contains(cours2));
        assertTrue(classe.getCoursClassrooms().contains(cours3));
    }
    
    @Test
    void testAddMultipleUtilisateurs() {
        Utilisateur user1 = new Utilisateur("John", "Doe", "pass1");
        Utilisateur user2 = new Utilisateur("Jane", "Smith", "pass2");
        Utilisateur user3 = new Utilisateur("Bob", "Johnson", "pass3");
        
        classe.addUtilisateur(user1);
        classe.addUtilisateur(user2);
        classe.addUtilisateur(user3);
        
        assertEquals(3, classe.getUtilisateurs().size());
        assertTrue(classe.getUtilisateurs().contains(user1));
        assertTrue(classe.getUtilisateurs().contains(user2));
        assertTrue(classe.getUtilisateurs().contains(user3));
    }
    
    @Test
    void testToString() {
        classe.setCodeClasse(1);
        CoursClassroom cours = new CoursClassroom(Specialite.INFORMATIQUE, "Java", 40);
        Utilisateur user = new Utilisateur("John", "Doe", "pass");
        classe.addCoursClassroom(cours);
        classe.addUtilisateur(user);
        
        String result = classe.toString();
        
        assertTrue(result.contains("codeClasse=1"));
        assertTrue(result.contains("titre='4INFO'"));
        assertTrue(result.contains("niveau=QUATRIEME"));
        assertTrue(result.contains("coursCount=1"));
        assertTrue(result.contains("utilisateurCount=1"));
    }
    
    @Test
    void testEquals_sameId() {
        Classe classe1 = new Classe("4INFO", Niveau.QUATRIEME);
        classe1.setCodeClasse(1);
        
        Classe classe2 = new Classe("5INFO", Niveau.CINQUIEME);
        classe2.setCodeClasse(1);
        
        assertEquals(classe1, classe2);
    }
    
    @Test
    void testEquals_differentId() {
        Classe classe1 = new Classe("4INFO", Niveau.QUATRIEME);
        classe1.setCodeClasse(1);
        
        Classe classe2 = new Classe("4INFO", Niveau.QUATRIEME);
        classe2.setCodeClasse(2);
        
        assertNotEquals(classe1, classe2);
    }
    
    @Test
    void testEquals_nullId() {
        Classe classe1 = new Classe("4INFO", Niveau.QUATRIEME);
        Classe classe2 = new Classe("4INFO", Niveau.QUATRIEME);
        
        // When both IDs are null, they should not be equal (transient entities)
        assertNotEquals(classe1, classe2);
    }
    
    @Test
    void testHashCode_consistency() {
        classe.setCodeClasse(1);
        int hashCode1 = classe.hashCode();
        int hashCode2 = classe.hashCode();
        
        assertEquals(hashCode1, hashCode2);
    }
    
    @Test
    void testSerializable() {
        assertTrue(classe instanceof java.io.Serializable);
    }
}
