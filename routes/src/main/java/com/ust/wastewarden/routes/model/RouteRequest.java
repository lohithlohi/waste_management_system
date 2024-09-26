package com.ust.wastewarden.routes.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
public class RouteRequest {
    private String mode;
    private List<Agent> agents;
    private List<Job> jobs;

    @Data
    public static class Agent {
        @JsonProperty("start_location")
        private double[] startLocation;
        @JsonProperty("end_location")
        private double[] endLocation;
        @JsonProperty("pickup_capacity")
        private int pickupCapacity;
    }

    @Data
    public static class Job {
        private double[] location;
        private int duration;
        @JsonProperty("pickup_amount")
        private int pickupAmount;
    }
}



/*
Record Version of Data Model

package com.ust.wastewarden.routes.model;

import java.util.List;

public record RouteRequest(
        String mode,
        List<Agent> agents,
        List<Job> jobs
) {
    public record Agent(
            double[] startLocation,
            double[] endLocation,
            int pickupCapacity
    ) {}

    public record Job(
            double[] location,
            int duration,
            int pickupAmount
    ) {}
}

 */