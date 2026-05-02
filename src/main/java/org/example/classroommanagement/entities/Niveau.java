package org.example.classroommanagement.entities;

/**
 * Enum representing the five academic levels in the university system.
 * Used in the Classe entity with @Enumerated(EnumType.STRING) to store values as strings in the database.
 */
public enum Niveau {
    PREMIERE,
    DEUXIEME,
    TROISIEME,
    QUATRIEME,
    CINQUIEME
}
