package org.example.classroommanagement.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.example.classroommanagement.entities.Niveau;
import org.example.classroommanagement.entities.Utilisateur;
import org.example.classroommanagement.services.IUtilisateurService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/utilisateurs")
@Tag(name = "Utilisateurs", description = "APIs for managing users")
public class UtilisateurController {

    private final IUtilisateurService utilisateurService;

    public UtilisateurController(IUtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    @PostMapping
    @Operation(summary = "Add a new user", description = "Creates a new user in the system")
    public ResponseEntity<Utilisateur> ajouterUtilisateur(@RequestBody Utilisateur utilisateur) {
        Utilisateur createdUser = utilisateurService.ajouterUtilisateur(utilisateur);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PutMapping("/{idUtilisateur}/classes/{codeClasse}")
    @Operation(summary = "Assign a user to a class", description = "Updates the user's class assignment")
    public ResponseEntity<Void> affecterUtilisateurClasse(
            @PathVariable Integer idUtilisateur,
            @PathVariable Integer codeClasse) {
        try {
            utilisateurService.affecterUtilisateurClasse(idUtilisateur, codeClasse);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/niveau/{niveau}")
    @Operation(summary = "Count users by academic level", description = "Returns the number of users for the specified niveau")
    public ResponseEntity<Integer> nbUtilisateursParNiveau(@PathVariable Niveau niveau) {
        Integer count = utilisateurService.nbUtilisateursParNiveau(niveau);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/count")
    @Operation(summary = "Count users by niveau (query param)", description = "Returns the number of users for the specified niveau via query parameter")
    public ResponseEntity<Integer> countUtilisateursByNiveau(@RequestParam("niveau") Niveau niveau) {
        Integer count = utilisateurService.nbUtilisateursParNiveau(niveau);
        return ResponseEntity.ok(count);
    }

    @GetMapping
    @Operation(summary = "Get all users", description = "Returns a list of all users")
    public ResponseEntity<List<Utilisateur>> getAllUtilisateurs() {
        List<Utilisateur> utilisateurs = utilisateurService.getAllUtilisateurs();
        return ResponseEntity.ok(utilisateurs);
    }

    @GetMapping("/{idUtilisateur}")
    @Operation(summary = "Get user by ID", description = "Returns the user with the specified ID")
    public ResponseEntity<Utilisateur> getUtilisateurById(@PathVariable Integer idUtilisateur) {
        try {
            Utilisateur utilisateur = utilisateurService.getUtilisateurById(idUtilisateur);
            return ResponseEntity.ok(utilisateur);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
