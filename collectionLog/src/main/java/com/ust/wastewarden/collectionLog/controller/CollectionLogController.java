package com.ust.wastewarden.collectionLog.controller;

import com.ust.wastewarden.collectionLog.model.CollectionLog;
import com.ust.wastewarden.collectionLog.service.CollectionLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/logs")
public class CollectionLogController {

    private final CollectionLogService collectionLogService;
    public CollectionLogController(CollectionLogService collectionLogService) {
        this.collectionLogService = collectionLogService;
    }

//    @PostMapping("/collect")
//    public ResponseEntity<CollectionLog> logCollection(
//            @RequestParam Long binId,
//            @RequestParam Long truckId,
//            @RequestParam Long routeId) {
//        CollectionLog log = collectionLogService.logCollection(binId, truckId, routeId);
//        return ResponseEntity.ok(log);
//    }
}
