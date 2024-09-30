package com.ust.wastewarden.issues.FeignClients;

import com.ust.wastewarden.issues.dtos.BinDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "bin-service")
public interface BinFeignClient {

    @GetMapping("/{id}")
    BinDTO getBinById(@PathVariable("id") Long id);
}
