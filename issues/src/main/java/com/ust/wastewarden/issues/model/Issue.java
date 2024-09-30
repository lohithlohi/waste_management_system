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

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Long userId;  // Stores the user ID fetched from the User service

    @Column(nullable = false)
    private Long binId;   // Stores the bin ID fetched from the Bin service

    @Enumerated(EnumType.STRING)
    private IssueStatus status = IssueStatus.PENDING;  // Issue status (e.g., PENDING, RESOLVED)

    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime resolvedAt;
}
