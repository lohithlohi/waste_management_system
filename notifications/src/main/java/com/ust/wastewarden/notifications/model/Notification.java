package com.ust.wastewarden.notifications.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;  // User who receives the notification
    private String message;

    @Enumerated(EnumType.STRING)
    private NotificationStatus status;  // UNREAD, READ

    private LocalDateTime createdAt;

    // Getters and setters
}
