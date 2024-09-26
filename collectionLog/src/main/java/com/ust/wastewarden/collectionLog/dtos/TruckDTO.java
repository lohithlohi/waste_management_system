package com.ust.wastewarden.collectionLog.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

import java.time.LocalDateTime;


@JsonIgnoreProperties(ignoreUnknown = true)
public record TruckDTO(
        Long id,

        String truckNumber,

        //int capacity,
        //String currentLocation,

        TruckStatus status,

        LocalDateTime createdAt,

        // Start coordinates
        double startLatitude,
        double startLongitude,

        // End coordinates
        double endLatitude,
        double endLongitude,

        Long routeId
) { }

enum TruckStatus {
    AVAILABLE,
    ON_ROUTE,
    MAINTENANCE
}
