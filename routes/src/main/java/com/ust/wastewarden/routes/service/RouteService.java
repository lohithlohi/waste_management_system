package com.ust.wastewarden.routes.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.ust.wastewarden.routes.model.Route;
import com.ust.wastewarden.routes.model.RouteData;
import com.ust.wastewarden.routes.model.RouteStatus;
import com.ust.wastewarden.routes.repository.RouteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RouteService {

    private final RouteRepository routeRepository;
    public RouteService(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<Route> getAllRoutes() {
        return routeRepository.findAll();
    }

    // Get all routes with pending status
    public List<Route> getPendingRoutes() {
        return routeRepository.findByStatus(RouteStatus.PENDING);
    }

    public Route getRouteById(Long id) {
        return routeRepository.findById(id).orElse(null);
    }

    // correction needed
    public Route saveRoute(Route route, RouteData routeData) throws Exception {
        route.setRouteData(objectMapper.writeValueAsString(routeData));
        return routeRepository.save(route);
    }

    // Assign a truck to the route
//    public Route assignTruckToRoute(Long routeId, Long truckId) {
//        // Check if the truck exists in the WorkTruck Service
//        String truckServiceUrl = "http://worktruck-service/trucks/" + truckId;
//        ResponseEntity<WorkTruckDTO> truckResponse = restTemplate.getForEntity(truckServiceUrl, WorkTruckDTO.class);
//        if (!truckResponse.hasBody()) {
//            throw new RuntimeException("Truck not found");
//        }
//
//        Route route = routeRepository.findById(routeId)
//                .orElseThrow(() -> new ResourceNotFoundException("Route not found"));
//
//        route.setTruckID(truckId);
//        return routeRepository.save(route);
//    }

    // Mark a route as completed
    public Route completeRoute(Long routeId) {
        Route route = routeRepository.findById(routeId).orElse(null);
//                .orElseThrow(() -> new ResourceNotFoundException("Route not found"));

        assert route != null;
        route.setStatus(RouteStatus.COMPLETED);
        return routeRepository.save(route);
    }

    public void deleteRoute(Long id) {
        routeRepository.deleteById(id);
    }

    public Route updateRoute(Long id, RouteData routeData) throws Exception {
        Route route = getRouteById(id);
        if (route != null) {
            route.setRouteData(objectMapper.writeValueAsString(routeData));
            return routeRepository.save(route);
        }
        return null;
    }
}

