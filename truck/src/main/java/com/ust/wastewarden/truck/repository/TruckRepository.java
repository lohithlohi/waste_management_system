package com.ust.wastewarden.truck.repository;

import com.ust.wastewarden.truck.model.Truck;
import com.ust.wastewarden.truck.model.TruckStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TruckRepository extends JpaRepository<Truck, Long> {
    List<Truck> findByStatus(TruckStatus status);
}
