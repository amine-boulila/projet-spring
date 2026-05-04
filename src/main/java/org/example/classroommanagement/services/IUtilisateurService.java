package org.example.classroommanagement.services;

import org.example.classroommanagement.entities.Niveau;
import org.example.classroommanagement.entities.Utilisateur;

import java.util.List;

/**
 * Service contract for user-related operations.
 */
public interface IUtilisateurService {

    Utilisateur ajouterUtilisateur(Utilisateur utilisateur);

    void affecterUtilisateurClasse(Integer idUtilisateur, Integer codeClasse);

    Integer nbUtilisateursParNiveau(Niveau niveau);

    List<Utilisateur> getAllUtilisateurs();

    Utilisateur getUtilisateurById(Integer idUtilisateur);
}
