package org.example.classroommanagement.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.example.classroommanagement.entities.Classe;
import org.example.classroommanagement.entities.Niveau;
import org.example.classroommanagement.entities.Utilisateur;
import org.example.classroommanagement.repositories.ClasseRepository;
import org.example.classroommanagement.repositories.UtilisateurRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service implementation for user-related operations.
 */
@Service
@Transactional
@Slf4j
public class UtilisateurServiceImpl implements IUtilisateurService {

    private final UtilisateurRepository utilisateurRepository;
    private final ClasseRepository classeRepository;

    public UtilisateurServiceImpl(
            UtilisateurRepository utilisateurRepository,
            ClasseRepository classeRepository) {
        this.utilisateurRepository = utilisateurRepository;
        this.classeRepository = classeRepository;
    }

    @Override
    public Utilisateur ajouterUtilisateur(Utilisateur utilisateur) {
        log.debug("Adding new user: {}", utilisateur);

        if (utilisateur.getPrenom() == null || utilisateur.getPrenom().trim().isEmpty()) {
            log.error("Validation failed: prenom is null or empty");
            throw new IllegalArgumentException("Prenom must not be null or empty");
        }

        if (utilisateur.getNom() == null || utilisateur.getNom().trim().isEmpty()) {
            log.error("Validation failed: nom is null or empty");
            throw new IllegalArgumentException("Nom must not be null or empty");
        }

        Utilisateur savedUser = utilisateurRepository.save(utilisateur);
        log.info("User added successfully with ID: {}", savedUser.getIdUtilisateur());
        return savedUser;
    }

    @Override
    public void affecterUtilisateurClasse(Integer idUtilisateur, Integer codeClasse) {
        log.debug("Assigning user {} to class {}", idUtilisateur, codeClasse);

        Utilisateur utilisateur = utilisateurRepository.findById(idUtilisateur)
                .orElseThrow(() -> {
                    log.error("User not found with ID: {}", idUtilisateur);
                    return new EntityNotFoundException("Utilisateur not found with ID: " + idUtilisateur);
                });

        Classe classe = classeRepository.findById(codeClasse)
                .orElseThrow(() -> {
                    log.error("Class not found with code: {}", codeClasse);
                    return new EntityNotFoundException("Classe not found with code: " + codeClasse);
                });

        utilisateur.setClasse(classe);
        classe.getUtilisateurs().add(utilisateur);

        utilisateurRepository.save(utilisateur);
        log.info("User {} assigned to class {} successfully", idUtilisateur, codeClasse);
    }

    @Override
    public Integer nbUtilisateursParNiveau(Niveau niveau) {
        log.debug("Counting users for niveau: {}", niveau);

        if (niveau == null) {
            log.error("Validation failed: niveau is null");
            throw new IllegalArgumentException("Niveau must not be null");
        }

        List<Utilisateur> utilisateurs = utilisateurRepository.findByClasse_Niveau(niveau);
        int count = utilisateurs.size();
        log.info("Found {} users for niveau: {}", count, niveau);
        return count;
    }

    @Override
    public List<Utilisateur> getAllUtilisateurs() {
        log.debug("Retrieving all users");
        List<Utilisateur> utilisateurs = utilisateurRepository.findAll();
        log.info("Found {} users", utilisateurs.size());
        return utilisateurs;
    }

    @Override
    public Utilisateur getUtilisateurById(Integer idUtilisateur) {
        log.debug("Retrieving user with ID: {}", idUtilisateur);

        Utilisateur utilisateur = utilisateurRepository.findById(idUtilisateur)
                .orElseThrow(() -> {
                    log.error("User not found with ID: {}", idUtilisateur);
                    return new EntityNotFoundException("Utilisateur not found with ID: " + idUtilisateur);
                });

        log.info("User found: {} {}", utilisateur.getPrenom(), utilisateur.getNom());
        return utilisateur;
    }
}
