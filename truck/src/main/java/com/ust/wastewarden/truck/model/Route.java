package com.ust.wastewarden.truck.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "routes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String geometry; // Store serialized geometry data (coordinates in JSON)

    private int distance;
    private int startTime;
    private int endTime;
    private String mode;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "route_id")
    private List<RouteAction> actions; // Actions along the route
}
