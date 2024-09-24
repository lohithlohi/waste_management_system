package com.ust.wastewarden.routes.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter @Setter
public class RouteData {

    private List<Waypoint> waypoints;
    private String totalDistance;
    private String estimatedTime;

    // Getters and Setters

    public static RouteData fromJson(String json) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, RouteData.class);
    }

    public String toJson() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(this);
    }
}

@Data
@Getter @Setter
class Waypoint {
    private Location location;
    private String name;
    private String ETA;

    // Getters and Setters
}

@Data
@Getter @Setter
class Location {
    private double latitude;
    private double longitude;

    // Getters and Setters
}
