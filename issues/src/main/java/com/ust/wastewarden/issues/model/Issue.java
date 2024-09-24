package com.ust.wastewarden.issues.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "issues")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Issue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;  // User reporting the issue
    private Long binId;   // Bin related to the issue
    private String description;

    @Enumerated(EnumType.STRING)
    private IssueStatus status;  // REPORTED, RESOLVED

    private LocalDateTime reportedAt;
    private LocalDateTime resolvedAt;

    // Getters and setters
}