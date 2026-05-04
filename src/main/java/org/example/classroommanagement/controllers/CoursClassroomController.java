package org.example.classroommanagement.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.example.classroommanagement.entities.CoursClassroom;
import org.example.classroommanagement.entities.Niveau;
import org.example.classroommanagement.entities.Specialite;
import org.example.classroommanagement.services.ICoursClassroomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cours")
@Tag(name = "Cours", description = "APIs for managing courses")
public class CoursClassroomController {

    private final ICoursClassroomService coursClassroomService;

    public CoursClassroomController(ICoursClassroomService coursClassroomService) {
        this.coursClassroomService = coursClassroomService;
    }

    @PostMapping("/{codeClasse}")
    @Operation(summary = "Add a new course to a class", description = "Creates a new course and associates it with the specified class")
    public ResponseEntity<CoursClassroom> ajouterCoursClassroom(
            @RequestBody CoursClassroom cours,
            @PathVariable Integer codeClasse) {
        try {
            CoursClassroom createdCours = coursClassroomService.ajouterCoursClassroom(cours, codeClasse);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdCours);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/desaffecter/{idCours}")
    @Operation(summary = "Unassign a course from its class", description = "Sets the course's classe relationship to null")
    public ResponseEntity<Void> desaffecterCoursClassroomClasse(@PathVariable Integer idCours) {
        try {
            coursClassroomService.desaffecterCoursClassroomClasse(idCours);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{idCours}/classe")
    @Operation(summary = "Unassign a course from its class (legacy path)", description = "Compatibility: Unassigns a course by id using legacy route")
    public ResponseEntity<Void> deleteDesaffecterCoursClassroomClasse(@PathVariable Integer idCours) {
        try {
            coursClassroomService.desaffecterCoursClassroomClasse(idCours);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/heures")
    @Operation(summary = "Calculate total hours by specialty and level", description = "Returns the sum of nbHeures for courses matching the specified specialite and niveau")
    public ResponseEntity<Integer> nbHeuresParSpecEtNiv(
            @RequestParam Specialite specialite,
            @RequestParam Niveau niveau) {
        Integer totalHours = coursClassroomService.nbHeuresParSpecEtNiv(specialite, niveau);
        return ResponseEntity.ok(totalHours);
    }
}
