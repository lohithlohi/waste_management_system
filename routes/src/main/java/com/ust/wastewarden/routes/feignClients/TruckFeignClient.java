package com.ust.wastewarden.routes.feignClients;

import com.ust.wastewarden.routes.model.Truck;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "TRUCK")
public interface TruckFeignClient {

    @GetMapping("/trucks/available")
    List<Truck> getAvailableTrucks();
}
