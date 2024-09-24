package com.ust.wastewarden.routes.model;

import lombok.Data;

import java.util.List;

@Data
public class RouteRequest {
    private String mode;
    private List<Agent> agents;
    private List<Job> jobs;

    // Getters and Setters

    @Data
    public static class Agent {
        private double[] start_location;
        private double[] end_location;
        private int pickup_capacity;

        // Getters and Setters
    }

    @Data
    public static class Job {
        private double[] location;
        private int duration;
        private int pickup_amount;

        // Getters and Setters
    }
}

