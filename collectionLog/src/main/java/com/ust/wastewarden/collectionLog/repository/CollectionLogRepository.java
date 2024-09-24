package com.ust.wastewarden.collectionLog.repository;

import com.ust.wastewarden.collectionLog.model.CollectionLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CollectionLogRepository extends JpaRepository<CollectionLog, Long> {
    List<CollectionLog> findByRouteId(Long routeId);
}
