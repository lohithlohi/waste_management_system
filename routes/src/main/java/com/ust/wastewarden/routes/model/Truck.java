package com.ust.wastewarden.routes.model;

import lombok.Data;

@Data
public class Truck {
    private double startLatitude;
    private double startLongitude;
    private double endLatitude;
    private double endLongitude;
    private int pickupCapacity;
}

