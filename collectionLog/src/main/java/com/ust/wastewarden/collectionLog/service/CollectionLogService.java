package com.ust.wastewarden.collectionLog.service;

import com.ust.wastewarden.collectionLog.dtos.BinDTO;
import com.ust.wastewarden.collectionLog.dtos.RouteDTO;
import com.ust.wastewarden.collectionLog.dtos.TruckDTO;
import com.ust.wastewarden.collectionLog.model.CollectionLog;
import com.ust.wastewarden.collectionLog.repository.CollectionLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Service
public class CollectionLogService {

    private final CollectionLogRepository collectionLogRepository;
    public CollectionLogService(CollectionLogRepository collectionLogRepository) {
        this.collectionLogRepository = collectionLogRepository;
    }

//    @Autowired
//    private RestTemplate restTemplate;


    // Log a collection activity
//    public CollectionLog logCollection(Long binId, Long truckId, Long routeId) {
//        // Check if the bin, truck, and route exist via respective services
//        String binServiceUrl = "http://bin-service/bins/" + binId;
//        String truckServiceUrl = "http://truck-service/trucks/" + truckId;
//        String routeServiceUrl = "http://route-service/routes/" + routeId;
//
//        restTemplate.getForEntity(binServiceUrl, BinDTO.class);
//        restTemplate.getForEntity(truckServiceUrl, TruckDTO.class);
//        restTemplate.getForEntity(routeServiceUrl, RouteDTO.class);
//
//        CollectionLog log = new CollectionLog();
//        log.setBinId(binId);
//        log.setTruckId(truckId);
//        log.setRouteId(routeId);
//        log.setCollectedAt(LocalDateTime.now());
//
//        return collectionLogRepository.save(log);
//    }
}
