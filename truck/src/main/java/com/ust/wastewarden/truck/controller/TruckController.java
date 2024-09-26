package com.ust.wastewarden.truck.controller;

import com.ust.wastewarden.truck.model.RouteResponse;
import com.ust.wastewarden.truck.model.Truck;
import com.ust.wastewarden.truck.model.TruckStatusUpdateRequest;
import com.ust.wastewarden.truck.service.TruckService;
import org.springframework.beans.factory.annotation.Autowired;
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
}
