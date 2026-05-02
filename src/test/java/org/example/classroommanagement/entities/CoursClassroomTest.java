package org.example.classroommanagement.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CoursClassroomTest {

    @Test
    void testDefaultConstructor() {
        CoursClassroom cours = new CoursClassroom();

        assertNotNull(cours);
        assertNull(cours.getIdCours());
        assertNull(cours.getSpecialite());
        assertNull(cours.getNom());
        assertNull(cours.getNbHeures());
        assertEquals(false, cours.getArchive());
        assertNull(cours.getClasse());
    }

    @Test
    void testSettersAndGetters() {
        CoursClassroom cours = new CoursClassroom();

        cours.setIdCours(1);
        cours.setSpecialite(Specialite.GENIECIVIL);
        cours.setNom("Structural Engineering");
        cours.setNbHeures(60);
        cours.setArchive(true);

        assertEquals(1, cours.getIdCours());
        assertEquals(Specialite.GENIECIVIL, cours.getSpecialite());
        assertEquals("Structural Engineering", cours.getNom());
        assertEquals(60, cours.getNbHeures());
        assertEquals(true, cours.getArchive());
    }

    @Test
    void testManyToOneRelationshipWithClasse() {
        Classe classe = new Classe();
        classe.setCodeClasse(1);
        classe.setTitre("4INFO");
        classe.setNiveau(Niveau.QUATRIEME);

        CoursClassroom cours = new CoursClassroom();
        cours.setSpecialite(Specialite.INFORMATIQUE);
        cours.setNom("Database Systems");
        cours.setNbHeures(45);
        cours.setClasse(classe);

        assertNotNull(cours.getClasse());
        assertEquals(classe, cours.getClasse());
        assertEquals("4INFO", cours.getClasse().getTitre());
        assertEquals(Niveau.QUATRIEME, cours.getClasse().getNiveau());
    }

    @Test
    void testArchiveDefaultValue() {
        CoursClassroom cours = new CoursClassroom();

        assertEquals(false, cours.getArchive());
    }

    @Test
    void testToString() {
        CoursClassroom cours = new CoursClassroom();
        cours.setIdCours(1);
        cours.setSpecialite(Specialite.INFORMATIQUE);
        cours.setNom("Web Development");
        cours.setNbHeures(50);
        cours.setArchive(false);

        String result = cours.toString();

        assertTrue(result.contains("idCours=1"));
        assertTrue(result.contains("specialite=INFORMATIQUE"));
        assertTrue(result.contains("nom='Web Development'"));
        assertTrue(result.contains("nbHeures=50"));
        assertTrue(result.contains("archive=false"));
    }

    @Test
    void testEquals_SameObject() {
        CoursClassroom cours = new CoursClassroom();
        cours.setIdCours(1);

        assertEquals(cours, cours);
    }

    @Test
    void testEquals_SameId() {
        CoursClassroom cours1 = new CoursClassroom();
        cours1.setIdCours(1);

        CoursClassroom cours2 = new CoursClassroom();
        cours2.setIdCours(1);

        assertEquals(cours1, cours2);
    }

    @Test
    void testEquals_DifferentId() {
        CoursClassroom cours1 = new CoursClassroom();
        cours1.setIdCours(1);

        CoursClassroom cours2 = new CoursClassroom();
        cours2.setIdCours(2);

        assertNotEquals(cours1, cours2);
    }

    @Test
    void testEquals_NullId() {
        CoursClassroom cours1 = new CoursClassroom();
        CoursClassroom cours2 = new CoursClassroom();

        assertNotEquals(cours1, cours2);
    }

    @Test
    void testEquals_Null() {
        CoursClassroom cours = new CoursClassroom();
        cours.setIdCours(1);

        assertNotEquals(cours, null);
    }

    @Test
    void testEquals_DifferentClass() {
        CoursClassroom cours = new CoursClassroom();
        cours.setIdCours(1);

        assertNotEquals(cours, new Object());
    }

    @Test
    void testHashCode_Consistency() {
        CoursClassroom cours = new CoursClassroom();
        cours.setIdCours(1);

        int hash1 = cours.hashCode();
        int hash2 = cours.hashCode();

        assertEquals(hash1, hash2);
    }

    @Test
    void testHashCode_SameId() {
        CoursClassroom cours1 = new CoursClassroom();
        cours1.setIdCours(1);

        CoursClassroom cours2 = new CoursClassroom();
        cours2.setIdCours(1);

        assertEquals(cours1.hashCode(), cours2.hashCode());
    }

    @Test
    void testSerializable() {
        CoursClassroom cours = new CoursClassroom();

        assertTrue(cours instanceof java.io.Serializable);
    }
}