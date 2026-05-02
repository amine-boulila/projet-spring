package org.example.classroommanagement.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.classroommanagement.entities.Classe;
import org.example.classroommanagement.services.IClassroomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/classes")
@Tag(name = "Classes", description = "APIs for managing classes")
public class ClasseController {

    private final IClassroomService classroomService;

    public ClasseController(IClassroomService classroomService) {
        this.classroomService = classroomService;
    }

    @PostMapping
    @Operation(summary = "Add a new class", description = "Creates a new class in the system")
    public ResponseEntity<Classe> ajouterClasse(@RequestBody Classe classe) {
        Classe createdClasse = classroomService.ajouterClasse(classe);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdClasse);
    }
}
