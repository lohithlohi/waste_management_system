package com.ust.wastewarden.routes.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
public class RouteResponse {
    private String type;
    private Properties properties;
    private List<Feature> features;

    @Data
    public static class Properties {
        private String mode;
        private Params params;
        private Issues issues;

        @Data
        public static class Params {
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

        @Data
        public static class Issues {
            @JsonProperty("unassigned_agents")
            private List<Integer> unassignedAgents;
        }
    }

    @Data
    public static class Feature {
        private Geometry geometry;
        private String type;
        private FeatureProperties properties;

        @Data
        public static class Geometry {
            private String type;
            private List<List<double[]>> coordinates;
        }

        @Data
        public static class FeatureProperties {
            @JsonProperty("agent_index")
            private int agentIndex;
            private int time;
            @JsonProperty("start_time")
            private int startTime;
            @JsonProperty("end_time")
            private int endTime;
            private int distance;
            private List<Leg> legs;
            private String mode;
            private List<Action> actions;

            @Data
            public static class Leg {
                private int time;
                private int distance;
                @JsonProperty("from_waypoint_index")
                private int fromWaypointIndex;
                @JsonProperty("to_waypoint_index")
                private int toWaypointIndex;
            }

            @Data
            public static class Action {
                private int index;
                private String type;
                @JsonProperty("start_time")
                private int startTime;
                private int duration;
                @JsonProperty("job_index")
                private int jobIndex;
                @JsonProperty("waypoint_index")
                private int waypointIndex;
            }
        }
    }
}
