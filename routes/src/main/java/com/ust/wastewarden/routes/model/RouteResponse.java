package com.ust.wastewarden.routes.model;

import lombok.Data;

import java.util.List;

@Data
public class RouteResponse {
    private String type;
    private Properties properties;
    private List<Feature> features;

    // Getters and Setters

    @Data
    public static class Properties {
        private String mode;
        private Params params;
        private Issues issues;

        // Getters and Setters

        @Data
        public static class Params {
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

        @Data
        public static class Issues {
            private List<Integer> unassigned_agents;

            // Getters and Setters
        }
    }

    @Data
    public static class Feature {
        private Geometry geometry;
        private String type;
        private FeatureProperties properties;

        // Getters and Setters

        public static class Geometry {
            private String type;
            private List<List<double[]>> coordinates;

            // Getters and Setters
        }

        public static class FeatureProperties {
            private int agent_index;
            private int time;
            private int start_time;
            private int end_time;
            private int distance;
            private List<Leg> legs;
            private String mode;
            private List<Action> actions;

            // Getters and Setters

            public static class Leg {
                private int time;
                private int distance;
                private int from_waypoint_index;
                private int to_waypoint_index;

                // Getters and Setters
            }

            public static class Action {
                private int index;
                private String type;
                private int start_time;
                private int duration;
                private int job_index;
                private int waypoint_index;

                // Getters and Setters
            }
        }
    }
}
