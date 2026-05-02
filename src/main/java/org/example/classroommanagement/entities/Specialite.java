package org.example.classroommanagement.entities;

/**
 * Enum representing the three academic specialties in the university system.
 * Used in the CoursClassroom entity with @Enumerated(EnumType.STRING) to store values as strings in the database.
 */
public enum Specialite {
    INFORMATIQUE,
    GENIECIVIL,
    AGRICULTURE
}
