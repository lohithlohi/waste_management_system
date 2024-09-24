package com.ust.wastewarden.routes.controller;

import com.ust.wastewarden.routes.model.Route;
import com.ust.wastewarden.routes.model.RouteData;
import com.ust.wastewarden.routes.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/routes")
public class RouteController {

    @Autowired
    private RouteService routeService;

    @GetMapping
    public List<Route> getAllRoutes() {
        return routeService.getAllRoutes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Route> getRouteById(@PathVariable Long id) {
        Route route = routeService.getRouteById(id);
        if (route != null) {
            return ResponseEntity.ok(route);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Get pending routes
    @GetMapping("/pending")
    public List<Route> getPendingRoutes() {
        return routeService.getPendingRoutes();
    }

//    @PostMapping
//    public ResponseEntity<Route> saveRoute(@RequestBody Route route) {
//        Route savedRoute = routeService.saveRoute(route);
//        return ResponseEntity.ok(savedRoute);
//    }

    // Assign a truck to the route
//    @PutMapping("/{routeId}/assign-truck/{truckId}")
//    public ResponseEntity<Route> assignTruckToRoute(@PathVariable Long routeId, @PathVariable Long truckId) {
//        Route route = routeService.assignTruckToRoute(routeId, truckId);
//        return ResponseEntity.ok(route);
//    }

    // Mark a route as completed
    @PutMapping("/{routeId}/complete")
    public ResponseEntity<Route> completeRoute(@PathVariable Long routeId) {
        Route route = routeService.completeRoute(routeId);
        return ResponseEntity.ok(route);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Route> updateRoute(@PathVariable Long id, @RequestBody RouteData routeData) throws Exception {
        Route updatedRoute = routeService.updateRoute(id, routeData);
        if (updatedRoute != null) {
            return ResponseEntity.ok(updatedRoute);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoute(@PathVariable Long id) {
        routeService.deleteRoute(id);
        return ResponseEntity.noContent().build();
    }
}