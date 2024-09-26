package com.ust.wastewarden.collectionLog.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;

import java.time.LocalDateTime;

// corrections needed
@JsonIgnoreProperties(ignoreUnknown = true)
public record RouteDTO(
        Long id,
        String name,
        RouteStatus status,

        // Start coordinates
        double startLatitude,
        double startLongitude,

        // End coordinates
        double endLatitude,
        double endLongitude
)
{ }

enum RouteStatus {
    PENDING,
    COMPLETED
}