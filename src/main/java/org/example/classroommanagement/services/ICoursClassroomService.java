package org.example.classroommanagement.services;

import org.example.classroommanagement.entities.CoursClassroom;
import org.example.classroommanagement.entities.Niveau;
import org.example.classroommanagement.entities.Specialite;

/**
 * Service contract for course-related operations.
 */
public interface ICoursClassroomService {

    CoursClassroom ajouterCoursClassroom(CoursClassroom coursClassroom, Integer codeClasse);

    void desaffecterCoursClassroomClasse(Integer idCours);

    void archiverCoursClassrooms();

    Integer nbHeuresParSpecEtNiv(Specialite specialite, Niveau niveau);
}
