package com.ust.wastewarden.truck.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ust.wastewarden.truck.model.*;
import com.ust.wastewarden.truck.repository.RouteRepository;
import com.ust.wastewarden.truck.repository.TruckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TruckService {

    private final TruckRepository truckRepository;
    private final RouteRepository routeRepository;
    public TruckService(TruckRepository truckRepository, RouteRepository routeRepository) {
        this.truckRepository = truckRepository;
        this.routeRepository = routeRepository;
    }

    public List<Truck> getAllTrucks() {
        return truckRepository.findAll();
    }

    public List<Truck> getAvailableTrucks() {
        return truckRepository.findByStatus(TruckStatus.AVAILABLE);
    }

    public void assignRoute(RouteResponse routeResponse) {
        for (RouteResponse.Feature feature : routeResponse.getFeatures()) {
            int agentIndex = feature.getProperties().getAgentIndex(); // truck's index
            Truck truck = truckRepository.findById((long) agentIndex).orElse(null);

            if (truck != null) {
                // Convert Feature to Route entity
                Route route = mapFeatureToRoute(feature);

                // Associate the route with the truck
                truck.setAssignedRoute(route);
                truck.setStatus(TruckStatus.ON_ROUTE);

                // Save the route and truck
                routeRepository.save(route); // Save route first
                truckRepository.save(truck);  // Save truck with updated route
            }
        }
    }

    private Route mapFeatureToRoute(RouteResponse.Feature feature) {
        Route route = new Route();
        route.setType(feature.getType());
        route.setGeometry(serializeGeometry(feature.getGeometry())); // Serialize geometry to JSON
        route.setDistance(feature.getProperties().getDistance());
        route.setStartTime(feature.getProperties().getStartTime());
        route.setEndTime(feature.getProperties().getEndTime());
        route.setMode(feature.getProperties().getMode());

        // Map the legs or actions if necessary
        route.setActions(mapActions(feature.getProperties().getActions()));

        return route;
    }

    private String serializeGeometry(RouteResponse.Feature.Geometry geometry) {
        // Convert the geometry object into a JSON string
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(geometry.getCoordinates());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<RouteAction> mapActions(List<RouteResponse.Feature.FeatureProperties.Action> actions) {
        return actions.stream().map(action -> {
            RouteAction routeAction = new RouteAction();
            routeAction.setIndex(action.getIndex());
            routeAction.setType(action.getType());
            routeAction.setStartTime(action.getStartTime());
            routeAction.setDuration(action.getDuration());
            routeAction.setJobIndex(action.getJobIndex());
            routeAction.setWaypointIndex(action.getWaypointIndex());
            return routeAction;
        }).collect(Collectors.toList());
    }


    /*

    public void assignRoute(RouteResponse routeResponse) {
        for (RouteResponse.Feature feature : routeResponse.getFeatures()) {
            int agentIndex = feature.getProperties().getAgentIndex(); // truck's index
            Truck truck = truckRepository.findById((long) agentIndex).orElse(null);
//                    .orElseThrow(() -> new TruckNotFoundException("Truck not found for agent index: " + agentIndex));

            // Convert Feature to Route entity
            Route route = mapFeatureToRoute(feature);

            // Save the route and associate it with the truck
            truck.setAssignedRoute(route);
            truck.setStatus(TruckStatus.ON_ROUTE);

            // Save both truck and route
            routeRepository.save(route); // Save route first
            truckRepository.save(truck);  // Save truck with updated route
        }
    }

    private Route mapFeatureToRoute(RouteResponse.Feature feature) {
        Route route = new Route();
        route.setType(feature.getType());
        route.setGeometry(feature.getGeometry().getCoordinates().toString()); // You can serialize the geometry coordinates
        route.setDistance(feature.getProperties().getDistance());
        route.setStartTime(feature.getProperties().getStartTime());
        route.setEndTime(feature.getProperties().getEndTime());
        route.setMode(feature.getProperties().getMode());

        // Map the legs or actions if necessary
        route.setActions(mapActions(feature.getProperties().getActions()));

        return route;
    }

    private List<RouteAction> mapActions(List<RouteResponse.Feature.FeatureProperties.Action> actions) {
        return actions.stream().map(action -> {
            RouteAction routeAction = new RouteAction();
            routeAction.setIndex(action.getIndex());
            routeAction.setType(action.getType());
            routeAction.setStartTime(action.getStartTime());
            routeAction.setDuration(action.getDuration());
            routeAction.setJobIndex(action.getJobIndex());
            routeAction.setWaypointIndex(action.getWaypointIndex());
            return routeAction;
        }).collect(Collectors.toList());
    }

    */

    public TruckRouteResponse getTruckRoute(Truck truck) {
        Route assignedRoute = truck.getAssignedRoute();
        if (assignedRoute == null) {
            throw new RuntimeException("No route assigned to truck: " + truck.getId());
        }

        // Create RouteResponse to structure the data
        RouteResponse routeResponse = new RouteResponse();
        routeResponse.setType(assignedRoute.getType());

        // Map the properties
        RouteResponse.Properties properties = new RouteResponse.Properties();
        properties.setMode(assignedRoute.getMode());

        // Populate features
        List<RouteResponse.Feature> features = new ArrayList<>();
        RouteResponse.Feature feature = new RouteResponse.Feature();

        // Populate geometry
        RouteResponse.Feature.Geometry geometry = new RouteResponse.Feature.Geometry();
        geometry.setType("MultiLineString"); // or whatever geometry type you have
        geometry.setCoordinates(deserializeGeometry(assignedRoute.getGeometry())); // Convert serialized geometry back to coordinates
        feature.setGeometry(geometry);

        // Populate feature properties
        RouteResponse.Feature.FeatureProperties featureProperties = new RouteResponse.Feature.FeatureProperties();
        featureProperties.setAgentIndex(truck.getId().intValue()); // Assuming truck id as agent index
        featureProperties.setStartTime(assignedRoute.getStartTime());
        featureProperties.setEndTime(assignedRoute.getEndTime());
        featureProperties.setDistance(assignedRoute.getDistance());
        featureProperties.setMode(assignedRoute.getMode());

        // Map actions (legs, etc.)
        featureProperties.setActions(assignedRoute.getActions().stream().map(this::mapRouteActionToResponse).collect(Collectors.toList()));
        feature.setProperties(featureProperties);

        // Add the feature to the list
        features.add(feature);
        routeResponse.setFeatures(features);

        return new TruckRouteResponse(
                truck.getId(),
                truck.getTruckNumber(),
                truck.getStatus().name(),
                truck.getCurrentLocation(),
                routeResponse
        );
    }

    // Helper method to deserialize the geometry string back to a list of coordinates
    private List<List<double[]>> deserializeGeometry(String geometryJson) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(geometryJson, new TypeReference<List<List<double[]>>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    // Helper method to map RouteAction to RouteResponse.Feature.FeatureProperties.Action
    private RouteResponse.Feature.FeatureProperties.Action mapRouteActionToResponse(RouteAction routeAction) {
        RouteResponse.Feature.FeatureProperties.Action action = new RouteResponse.Feature.FeatureProperties.Action();
        action.setIndex(routeAction.getIndex());
        action.setType(routeAction.getType());
        action.setStartTime(routeAction.getStartTime());
        action.setDuration(routeAction.getDuration());
        action.setJobIndex(routeAction.getJobIndex());
        action.setWaypointIndex(routeAction.getWaypointIndex());
        return action;
    }



    public void updateTruckStatus(TruckStatusUpdateRequest request) {
        Truck truck = truckRepository.findById(request.getTruckId()).orElse(null);
//                .orElseThrow(() -> new TruckNotFoundException("Truck not found"));

        truck.setStatus(request.getNewStatus());
        truckRepository.save(truck);
    }

    public Truck getTruckById(Long id) {
        return truckRepository.findById(id).orElse(null);
    }

    public Truck saveTruck(Truck truck) {
        truck.setCreatedAt(LocalDateTime.now());
        return truckRepository.save(truck);
    }

    public List<Truck> saveAllTrucks(List<Truck> trucks){
        return truckRepository.saveAll(trucks);
    }

    public void deleteTruck(Long id) {
        truckRepository.deleteById(id);
    }

    public Truck updateTruck(Truck truck) {
        return truckRepository.save(truck);
    }

    // Change truck status (e.g., set to ON_ROUTE)
    public Truck updateTruckStatus(Long id, TruckStatus status) {
        Truck truck = getTruckById(id);
        truck.setStatus(status);
        return truckRepository.save(truck);
    }

    public Route getTruckRouteByTruckId(Long truckId) {
        Truck truck = truckRepository.findById(truckId).orElse(null);
//                .orElseThrow(() -> new ResourceNotFoundException("Truck not found"));
        return truck.getAssignedRoute();  // Assuming the route is stored as part of the truck entity
    }


    @Autowired
    private RouteMapper routeMapper;

    public RouteResponse getTruckRouteData(Long truckId) {
        Truck truck = truckRepository.findById(truckId).orElse(null);
//                .orElseThrow(() -> new TruckNotFoundException("Truck not found"));

        Route assignedRoute = truck.getAssignedRoute();
        return routeMapper.mapRouteToRouteResponse(assignedRoute);
    }


//     for Coordinates:
    public List<TruckCoordinatesResponse> getAllTrucksCoordinates() {
        List<Truck> trucks = truckRepository.findAll();
        return trucks.stream()
                .map(this::mapToTruckCoordinatesResponse)
                .collect(Collectors.toList());
    }

    public TruckCoordinatesResponse getTruckCoordinates(Long truckId) {
        Truck truck = truckRepository.findById(truckId).orElse(null);
//                .orElseThrow(() -> new TruckNotFoundException("Truck not found"));
        return mapToTruckCoordinatesResponse(truck);
    }

    private TruckCoordinatesResponse mapToTruckCoordinatesResponse(Truck truck) {
        TruckCoordinatesResponse response = new TruckCoordinatesResponse();
        response.setTruckId(truck.getId());
        response.setTruckNumber(truck.getTruckNumber());
        response.setStartCoordinates(new double[]{truck.getStartLatitude(), truck.getStartLongitude()});
        response.setEndCoordinates(new double[]{truck.getEndLatitude(), truck.getEndLongitude()});
        return response;
    }


    public List<TruckWithJobsResponse> getAllTrucksWithJobs() {
        List<Truck> trucks = truckRepository.findByAssignedRouteNotNull();
        return trucks.stream()
                .map(this::mapToTruckWithJobsResponse)
                .collect(Collectors.toList());
    }

    public TruckWithJobsResponse getTruckWithJobs(Long truckId) {
        Truck truck = truckRepository.findByIdAndAssignedRouteNotNull(truckId);
//                .orElseThrow(() -> new TruckNotFoundException("Truck not found or no jobs assigned"));
        return mapToTruckWithJobsResponse(truck);
    }

    private TruckWithJobsResponse mapToTruckWithJobsResponse(Truck truck) {
        TruckWithJobsResponse response = new TruckWithJobsResponse();
        response.setTruckId(truck.getId());
        response.setTruckNumber(truck.getTruckNumber());
        response.setAssignedRoute(truck.getAssignedRoute());
        return response;
    }


}
