package com.ust.wastewarden.truck.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RouteAction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int index;
    private String type;
    private int startTime;
    private int duration;
    private int jobIndex;
    private int waypointIndex;

    // Getters and setters
}