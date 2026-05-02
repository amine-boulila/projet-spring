package org.example.classroommanagement.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClasseTest {

    private Classe classe;

    @BeforeEach
    void setUp() {
        classe = new Classe();
        classe.setTitre("4INFO");
        classe.setNiveau(Niveau.QUATRIEME);
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
    void testSettersAndGetters() {
        classe.setCodeClasse(1);
        classe.setTitre("5INFO");
        classe.setNiveau(Niveau.CINQUIEME);

        assertEquals(1, classe.getCodeClasse());
        assertEquals("5INFO", classe.getTitre());
        assertEquals(Niveau.CINQUIEME, classe.getNiveau());
    }

    @Test
    void testCollectionsAreMutable() {
        CoursClassroom cours = new CoursClassroom();
        cours.setSpecialite(Specialite.INFORMATIQUE);
        cours.setNom("Java Programming");
        cours.setNbHeures(40);
        cours.setClasse(classe);
        classe.getCoursClassrooms().add(cours);

        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setPrenom("John");
        utilisateur.setNom("Doe");
        utilisateur.setPassword("password123");
        utilisateur.setClasse(classe);
        classe.getUtilisateurs().add(utilisateur);

        assertEquals(1, classe.getCoursClassrooms().size());
        assertEquals(1, classe.getUtilisateurs().size());
        assertEquals(classe, cours.getClasse());
        assertEquals(classe, utilisateur.getClasse());
    }

    @Test
    void testToString() {
        classe.setCodeClasse(1);

        String result = classe.toString();

        assertTrue(result.contains("codeClasse=1"));
        assertTrue(result.contains("titre='4INFO'"));
        assertTrue(result.contains("niveau=QUATRIEME"));
    }

    @Test
    void testEquals_sameId() {
        Classe classe1 = new Classe();
        classe1.setCodeClasse(1);

        Classe classe2 = new Classe();
        classe2.setCodeClasse(1);

        assertEquals(classe1, classe2);
    }

    @Test
    void testEquals_differentId() {
        Classe classe1 = new Classe();
        classe1.setCodeClasse(1);

        Classe classe2 = new Classe();
        classe2.setCodeClasse(2);

        assertNotEquals(classe1, classe2);
    }

    @Test
    void testEquals_nullId() {
        Classe classe1 = new Classe();
        Classe classe2 = new Classe();

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