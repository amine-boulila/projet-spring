package org.example.classroommanagement.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.example.classroommanagement.entities.Classe;
import org.example.classroommanagement.entities.CoursClassroom;
import org.example.classroommanagement.entities.Niveau;
import org.example.classroommanagement.entities.Specialite;
import org.example.classroommanagement.repositories.ClasseRepository;
import org.example.classroommanagement.repositories.CoursClassroomRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service implementation for course-related operations.
 */
@Service
@Transactional
@Slf4j
public class CoursClassroomServiceImpl implements ICoursClassroomService {

    private final ClasseRepository classeRepository;
    private final CoursClassroomRepository coursClassroomRepository;

    public CoursClassroomServiceImpl(
            ClasseRepository classeRepository,
            CoursClassroomRepository coursClassroomRepository) {
        this.classeRepository = classeRepository;
        this.coursClassroomRepository = coursClassroomRepository;
    }

    @Override
    public CoursClassroom ajouterCoursClassroom(CoursClassroom coursClassroom, Integer codeClasse) {
        log.debug("Adding new course: {} to class: {}", coursClassroom, codeClasse);

        if (coursClassroom.getNom() == null || coursClassroom.getNom().trim().isEmpty()) {
            log.error("Validation failed: nom is null or empty");
            throw new IllegalArgumentException("Nom must not be null or empty");
        }

        if (coursClassroom.getNbHeures() == null || coursClassroom.getNbHeures() <= 0) {
            log.error("Validation failed: nbHeures is null or not positive");
            throw new IllegalArgumentException("NbHeures must be positive");
        }

        Classe classe = classeRepository.findById(codeClasse)
                .orElseThrow(() -> {
                    log.error("Class not found with code: {}", codeClasse);
                    return new EntityNotFoundException("Classe not found with code: " + codeClasse);
                });

        coursClassroom.setClasse(classe);
        classe.getCoursClassrooms().add(coursClassroom);

        CoursClassroom savedCours = coursClassroomRepository.save(coursClassroom);
        log.info("Course added successfully with ID: {} to class: {}", savedCours.getIdCours(), codeClasse);
        return savedCours;
    }

    @Override
    public void desaffecterCoursClassroomClasse(Integer idCours) {
        log.debug("Unassigning course {} from its class", idCours);

        CoursClassroom cours = coursClassroomRepository.findById(idCours)
                .orElseThrow(() -> {
                    log.error("Course not found with ID: {}", idCours);
                    return new EntityNotFoundException("CoursClassroom not found with ID: " + idCours);
                });

        if (cours.getClasse() != null) {
            cours.getClasse().getCoursClassrooms().remove(cours);
        }

        cours.setClasse(null);
        coursClassroomRepository.save(cours);
        log.info("Course {} unassigned from its class successfully", idCours);
    }

    @Scheduled(fixedRate = 60000)
    @Override
    public void archiverCoursClassrooms() {
        log.debug("Starting scheduled archiving of all courses");

        List<CoursClassroom> allCours = coursClassroomRepository.findAll();
        for (CoursClassroom cours : allCours) {
            cours.setArchive(true);
        }

        coursClassroomRepository.saveAll(allCours);
        log.info("Archived {} courses successfully", allCours.size());
    }

    @Override
    public Integer nbHeuresParSpecEtNiv(Specialite specialite, Niveau niveau) {
        log.debug("Calculating total hours for specialite: {} and niveau: {}", specialite, niveau);

        if (specialite == null) {
            log.error("Validation failed: specialite is null");
            throw new IllegalArgumentException("Specialite must not be null");
        }

        if (niveau == null) {
            log.error("Validation failed: niveau is null");
            throw new IllegalArgumentException("Niveau must not be null");
        }

        List<CoursClassroom> cours = coursClassroomRepository.findBySpecialiteAndClasse_Niveau(specialite, niveau);
        int totalHeures = cours.stream().mapToInt(CoursClassroom::getNbHeures).sum();

        log.info("Total hours for specialite {} and niveau {}: {}", specialite, niveau, totalHeures);
        return totalHeures;
    }
}
