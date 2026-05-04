package org.example.classroommanagement.services;

import lombok.extern.slf4j.Slf4j;
import org.example.classroommanagement.entities.Classe;
import org.example.classroommanagement.repositories.ClasseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service implementation for class-related operations.
 */
@Service
@Transactional
@Slf4j
public class ClasseServiceImpl implements IClasseService {

    private final ClasseRepository classeRepository;

    public ClasseServiceImpl(ClasseRepository classeRepository) {
        this.classeRepository = classeRepository;
    }

    @Override
    public Classe ajouterClasse(Classe classe) {
        log.debug("Adding new class: {}", classe);

        if (classe.getTitre() == null || classe.getTitre().trim().isEmpty()) {
            log.error("Validation failed: titre is null or empty");
            throw new IllegalArgumentException("Titre must not be null or empty");
        }

        if (classe.getNiveau() == null) {
            log.error("Validation failed: niveau is null");
            throw new IllegalArgumentException("Niveau must not be null");
        }

        Classe savedClasse = classeRepository.save(classe);
        log.info("Class added successfully with code: {}", savedClasse.getCodeClasse());
        return savedClasse;
    }
}
