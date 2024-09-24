package com.ust.wastewarden.collectionLog.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "collectionlog")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CollectionLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long binId;  // Bin being collected
    private Long truckId;  // Truck performing the collection
    private Long routeId;  // Route on which the collection is happening
    private LocalDateTime collectedAt;

    // Getters and setters
}
