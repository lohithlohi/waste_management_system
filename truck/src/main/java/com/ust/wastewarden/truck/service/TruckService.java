package com.ust.wastewarden.truck.service;

import com.ust.wastewarden.truck.model.*;
import com.ust.wastewarden.truck.repository.RouteRepository;
import com.ust.wastewarden.truck.repository.TruckRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
}
