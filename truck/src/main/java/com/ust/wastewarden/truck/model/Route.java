package com.ust.wastewarden.truck.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;
    private String geometry; // This can be serialized as JSON for storing coordinates
    private int distance;
    private int startTime;
    private int endTime;
    private String mode;

    @OneToMany(cascade = CascadeType.ALL)
    private List<RouteAction> actions; // Actions along the route

    // Getters and setters
}