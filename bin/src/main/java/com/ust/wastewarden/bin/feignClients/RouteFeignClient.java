package com.ust.wastewarden.bin.feignClients;

import com.ust.wastewarden.bin.model.Bin;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "ROUTES")
public interface RouteFeignClient {

    @PostMapping("/routes/jobs")
    public void assignJobs(@RequestBody List<Bin> bins);
}
