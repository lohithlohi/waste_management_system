package com.ust.wastewarden.truck.model;

import lombok.Data;

@Data
public class TruckStatusUpdateRequest {
    private Long truckId;
    private TruckStatus newStatus;

    // Getters and setters
}