package com.ust.wastewarden.truck.controller;

import com.ust.wastewarden.truck.model.*;
import com.ust.wastewarden.truck.service.TruckService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trucks")
public class TruckController {

    private final TruckService truckService;
    public TruckController(TruckService truckService) {
        this.truckService = truckService;
    }

    @GetMapping
    public List<Truck> getAllTrucks() {
        return truckService.getAllTrucks();
    }

    // Get available trucks
    @GetMapping("/available")
    public List<Truck> getAvailableTrucks() {
        return truckService.getAvailableTrucks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Truck> getTruckById(@PathVariable Long id) {
        Truck truck = truckService.getTruckById(id);
        if (truck != null) {
            return ResponseEntity.ok(truck);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Truck> saveTruck(@RequestBody Truck truck) {
        Truck savedTruck = truckService.saveTruck(truck);
        return ResponseEntity.ok(savedTruck);
    }

    @PostMapping("/saveall")
    public ResponseEntity<List<Truck>> saveTrucks(@RequestBody List<Truck> trucks) {
        List<Truck> savedTrucks = truckService.saveAllTrucks(trucks);
        return ResponseEntity.ok(savedTrucks);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Truck> updateTruck(@PathVariable Long id, @RequestBody Truck truck) {
        truck.setId(id);
        Truck updatedTruck = truckService.updateTruck(truck);
        if (updatedTruck != null) {
            return ResponseEntity.ok(updatedTruck);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTruck(@PathVariable Long id) {
        truckService.deleteTruck(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/assign-route")
    public void assignRouteToTruck(@RequestBody RouteResponse routeResponse) {
        truckService.assignRoute(routeResponse);
    }

    public void updateTruckStatus(@RequestBody TruckStatusUpdateRequest statusUpdateRequest) {
        truckService.updateTruckStatus(statusUpdateRequest);
    }

//    @GetMapping("/{truckId}/routes")
//    public Route getTruckRoutes(@PathVariable Long truckId) {
//        return truckService.getTruckRouteByTruckId(truckId);
//    }

    // Endpoint to get truck route
    @GetMapping("/{truckId}/route1")
    public ResponseEntity<TruckRouteResponse> getTruckRoute(@PathVariable Long truckId) {
        Truck truck = truckService.getTruckById(truckId);

        if (truck != null && truck.getAssignedRoute() != null) {
            TruckRouteResponse truckRouteResponse = truckService.getTruckRoute(truck);
            return ResponseEntity.ok(truckRouteResponse);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint to get truck route data for plotting on the map
    @GetMapping("/{id}/route2")
    public ResponseEntity<RouteResponse> getTruckRouteData(@PathVariable Long id) {
        RouteResponse routeResponse = truckService.getTruckRouteData(id);
        return ResponseEntity.ok(routeResponse);
    }

    // Endpoint to get start and end coordinates of all trucks
    @GetMapping("/coordinates")
    public ResponseEntity<List<TruckCoordinatesResponse>> getAllTrucksCoordinates() {
        List<TruckCoordinatesResponse> truckCoordinates = truckService.getAllTrucksCoordinates();
        return ResponseEntity.ok(truckCoordinates);
    }

    // Endpoint to get start and end coordinates of a specific truck by ID
    @GetMapping("/{id}/coordinates")
    public ResponseEntity<TruckCoordinatesResponse> getTruckCoordinates(@PathVariable Long id) {
        TruckCoordinatesResponse truckCoordinates = truckService.getTruckCoordinates(id);
        return ResponseEntity.ok(truckCoordinates);
    }


    // Endpoint to get all trucks with assigned jobs
    @GetMapping("/with-jobs")
    public ResponseEntity<List<TruckWithJobsResponse>> getAllTrucksWithJobs() {
        List<TruckWithJobsResponse> trucksWithJobs = truckService.getAllTrucksWithJobs();
        return ResponseEntity.ok(trucksWithJobs);
    }

    // Endpoint to get a specific truck with assigned jobs
    @GetMapping("/{id}/with-jobs")
    public ResponseEntity<TruckWithJobsResponse> getTruckWithJobs(@PathVariable Long id) {
        TruckWithJobsResponse truckWithJobs = truckService.getTruckWithJobs(id);
        return ResponseEntity.ok(truckWithJobs);
    }

}
