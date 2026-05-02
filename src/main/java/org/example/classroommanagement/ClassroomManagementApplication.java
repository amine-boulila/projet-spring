package org.example.classroommanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Main Spring Boot application class for the Classroom Management System.
 * Enables scheduling for automated tasks like course archiving.
 */
@SpringBootApplication
@EnableScheduling
public class ClassroomManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClassroomManagementApplication.class, args);
    }
}
