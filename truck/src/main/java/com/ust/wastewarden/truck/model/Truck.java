package com.ust.wastewarden.truck.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "trucks")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Truck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String truckNumber;

    private int pickupCapacity;
    private String currentLocation;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TruckStatus status = TruckStatus.AVAILABLE;

    private LocalDateTime createdAt = LocalDateTime.now();

    // Start coordinates
    private double startLatitude;
    private double startLongitude;

    // End coordinates
    private double endLatitude;
    private double endLongitude;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "assigned_route_id", referencedColumnName = "id")
    private Route assignedRoute; // Store the assigned route

}


//@Entity
//@Table(name = "trucks")
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@Getter
//@Setter
//public class Truck {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(unique = true)
//    private String truckNumber;
//
//    private int capacity;
//    private String currentLocation;
//
//    @Enumerated(EnumType.STRING)
//    @Column(nullable = false)
//    private TruckStatus status = TruckStatus.AVAILABLE;
//
//    private LocalDateTime createdAt = LocalDateTime.now();
//
//    // Start coordinates
//    private double startLatitude;
//    private double startLongitude;
//
//    // End coordinates
//    private double endLatitude;
//    private double endLongitude;
//
//    @OneToOne(cascade = CascadeType.ALL)
//    private Route assignedRoute; // Store the assigned route
//
//    // One truck can have many routes assigned to it
////    @OneToMany(mappedBy = "assignedTruck", cascade = CascadeType.ALL)
////    private List<Route> routes;
//
////    like one truck allowed for only one route (ONE to ONE)
////    private Long routeId;
//}

//Table work_trucks {
//id BIGINT [pk, increment]
//truck_number VARCHAR(255) [unique]
//capacity INT
//current_location VARCHAR(255)
//status ENUM('AVAILABLE', 'ON_ROUTE', 'MAINTENANCE') [default: 'AVAILABLE']
//created_at TIMESTAMP
//}