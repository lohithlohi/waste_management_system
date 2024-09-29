package com.ust.wastewarden.truck.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class TruckWithJobsResponse {
    private Long truckId;
    private String truckNumber;
    private Route assignedRoute;
}
