package com.ust.wastewarden.routes.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "routes")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RouteStatus status = RouteStatus.PENDING;

//    @JsonIgnore // To avoid circular reference
//    @ManyToOne
//    @JoinColumn(name = "assigned_truck_id") // Foreign key in the routes table
//    private Truck assignedTruck;

    private Long truckID;

//    ***** correction needed for this based on maps api data fetch
    @Column(columnDefinition = "json")
    private String routeData; // Storing route data as JSON

    private LocalDateTime createdAt = LocalDateTime.now();

    // Start coordinates
    private double startLatitude;
    private double startLongitude;

    // End coordinates
    private double endLatitude;
    private double endLongitude;
}


//Table routes {
//id BIGINT [pk, increment]
//name VARCHAR(255)
//status ENUM('PENDING', 'COMPLETED') [default: 'PENDING']
//assigned_truck_id BIGINT
//route_data JSON
//created_at TIMESTAMP
//}

