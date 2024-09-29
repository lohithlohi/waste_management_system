package com.ust.wastewarden.truck.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TruckRouteResponse {
    private Long truckId;
    private String truckNumber;
    private String truckStatus;
    private String currentLocation;
    private RouteResponse assignedRoute;
}
