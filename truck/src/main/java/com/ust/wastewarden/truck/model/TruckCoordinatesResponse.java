package com.ust.wastewarden.truck.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class TruckCoordinatesResponse {
    private Long truckId;
    private String truckNumber;
    private double[] startCoordinates;
    private double[] endCoordinates;
}
