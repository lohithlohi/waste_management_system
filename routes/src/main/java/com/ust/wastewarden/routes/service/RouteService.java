package com.ust.wastewarden.routes.service;


import com.fasterxml.jackson.databind.ObjectMapper;
//import com.ust.wastewarden.routes.feignClients.NotificationFeignClient;
import com.ust.wastewarden.routes.feignClients.TruckFeignClient;
import com.ust.wastewarden.routes.model.*;
import com.ust.wastewarden.routes.repository.RouteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RouteService {

    private final TruckFeignClient truckFeignClient;
//    private final NotificationFeignClient notificationFeignClient;
    private final RoutePlannerService routePlannerService;
    private final RouteRepository routeRepository;
    public RouteService(TruckFeignClient truckFeignClient, RoutePlannerService routePlannerService, RouteRepository routeRepository) {
        this.truckFeignClient = truckFeignClient;
//        this.notificationFeignClient = notificationFeignClient;
        this.routePlannerService = routePlannerService;
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

    // Assign routes based on bins (jobs) and available trucks (agents)
    public RouteResponse assignRoutes(List<Bin> bins) throws Exception {
        // Step 1: Fetch available trucks
        List<Truck> trucks = truckFeignClient.getAvailableTrucks();

        // Step 2: Build the route request using bins and trucks
        RouteRequest routeRequest = buildRouteRequest(bins, trucks);

        // Step 3: Call the RoutePlannerService for optimized routes
        RouteResponse optimizedRoute = routePlannerService.getOptimizedRoute(routeRequest);

        // Step 4: Send the optimized routes to the Truck Microservice
        truckFeignClient.assignRouteToTruck(optimizedRoute);

        return optimizedRoute;

        // Step 5: Notify the trucks via Notification Microservice
//        notificationFeignClient.notifyTrucks(optimizedRoute);
    }

    // Build the RouteRequest based on the bins and trucks data
    private RouteRequest buildRouteRequest(List<Bin> bins, List<Truck> trucks) {
        RouteRequest routeRequest = new RouteRequest();
        routeRequest.setMode("truck");

        // Prepare the agent list (trucks)
        List<RouteRequest.Agent> agents = trucks.stream().map(truck -> {
            RouteRequest.Agent agent = new RouteRequest.Agent();
            agent.setStartLocation(new double[]{truck.getStartLatitude(), truck.getStartLongitude()});
            agent.setEndLocation(new double[]{truck.getEndLatitude(), truck.getEndLongitude()});
            agent.setPickupCapacity(truck.getPickupCapacity());
            return agent;
        }).collect(Collectors.toList());

        // Prepare the job list (bins)
        List<RouteRequest.Job> jobs = bins.stream().map(bin -> {
            RouteRequest.Job job = new RouteRequest.Job();
            job.setLocation(new double[]{bin.getLatitude(), bin.getLongitude()});
            job.setDuration(300); // Assuming 5 minutes per job
            job.setPickupAmount(bin.getWasteAmount());
            return job;
        }).collect(Collectors.toList());

        routeRequest.setAgents(agents);
        routeRequest.setJobs(jobs);

        return routeRequest;
    }

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

