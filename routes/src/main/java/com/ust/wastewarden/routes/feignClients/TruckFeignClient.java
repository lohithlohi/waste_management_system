package com.ust.wastewarden.routes.feignClients;

import com.ust.wastewarden.routes.model.RouteResponse;
import com.ust.wastewarden.routes.model.Truck;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "TRUCK")
public interface TruckFeignClient {

    @GetMapping("/trucks/available")
    List<Truck> getAvailableTrucks();

    @PostMapping("/trucks/assign-route")
    void assignRouteToTruck(@RequestBody RouteResponse routeResponse);
}
