package com.ust.wastewarden.truck.service;

import com.ust.wastewarden.truck.model.Truck;
import com.ust.wastewarden.truck.model.TruckStatus;
import com.ust.wastewarden.truck.repository.TruckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TruckService {

    private final TruckRepository truckRepository;
    public TruckService(TruckRepository truckRepository) {
        this.truckRepository = truckRepository;
    }

    public List<Truck> getAllTrucks() {
        return truckRepository.findAll();
    }

    public List<Truck> getAvailableTrucks() {
        return truckRepository.findByStatus(TruckStatus.AVAILABLE);
    }

    public Truck getTruckById(Long id) {
        return truckRepository.findById(id).orElse(null);
    }

    public Truck saveTruck(Truck truck) {
        truck.setCreatedAt(LocalDateTime.now());
        return truckRepository.save(truck);
    }

    public void deleteTruck(Long id) {
        truckRepository.deleteById(id);
    }

    public Truck updateTruck(Truck truck) {
        return truckRepository.save(truck);
    }

    // Change truck status (e.g., set to ON_ROUTE)
    public Truck updateTruckStatus(Long id, TruckStatus status) {
        Truck truck = getTruckById(id);
        truck.setStatus(status);
        return truckRepository.save(truck);
    }
}
