package com.ust.wastewarden.truck.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ust.wastewarden.truck.model.Route;
import com.ust.wastewarden.truck.model.RouteResponse;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class RouteMapper {

    public RouteResponse mapRouteToRouteResponse(Route route) {
        RouteResponse routeResponse = new RouteResponse();
        routeResponse.setType(route.getType());
        
        RouteResponse.Feature feature = new RouteResponse.Feature();
        feature.setType(route.getType());

        // Parse geometry
        RouteResponse.Feature.Geometry geometry = new RouteResponse.Feature.Geometry();
        geometry.setType("MultiLineString");
        geometry.setCoordinates(parseGeometry(route.getGeometry()));
        feature.setGeometry(geometry);

        RouteResponse.Feature.FeatureProperties properties = new RouteResponse.Feature.FeatureProperties();
        properties.setDistance(route.getDistance());
        properties.setStartTime(route.getStartTime());
        properties.setEndTime(route.getEndTime());
        feature.setProperties(properties);

        routeResponse.setFeatures(Collections.singletonList(feature));

        return routeResponse;
    }

//    private List<List<double[]>> parseGeometry(String geometry) {
//        // Assuming geometry is serialized as a JSON array
//        return new Gson().fromJson(geometry, new TypeToken<List<List<double[]>>>() {}.getType());
//    }

    private List<List<double[]>> parseGeometry(String geometry) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // Assuming geometry is serialized as a JSON array of coordinates
            return objectMapper.readValue(geometry, new TypeReference<List<List<double[]>>>() {});
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse geometry JSON", e);
        }
    }
}
