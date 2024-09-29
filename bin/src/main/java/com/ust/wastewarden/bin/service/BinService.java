package com.ust.wastewarden.bin.service;

import com.ust.wastewarden.bin.feignClients.RouteFeignClient;
import com.ust.wastewarden.bin.model.Bin;
import com.ust.wastewarden.bin.model.BinStatus;
import com.ust.wastewarden.bin.repository.BinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class BinService {

    private final BinRepository binRepository;
    private final RouteFeignClient routeFeignClient;
    public BinService(BinRepository binRepository, RouteFeignClient routeFeignClient) {
        this.binRepository = binRepository;
        this.routeFeignClient = routeFeignClient;
    }

    private Random random = new Random();


    public List<Bin> getAllBins() {
        return binRepository.findAll();
    }

    public List<Bin> saveAllBins(List<Bin> bins){
        return binRepository.saveAll(bins);
    }

    public Bin getBinById(Long id) {
        return binRepository.findById(id).orElse(null);
    }

    public Bin saveBin(Bin bin) {
        return binRepository.save(bin);
    }

    public void deleteBin(Long id) {
        binRepository.deleteById(id);
    }

    // Fetch bins with both FULL and OVERFLOW statuses
    public List<Bin> getFullAndOverflowingBins() {
        return binRepository.findByStatusIn(Arrays.asList("FULL", "OVERFLOWING"));
    }

    public Bin updateBins(Bin bin , Long id) {
        Optional<Bin> optionalBin = binRepository.findById(id);
        if(optionalBin.isPresent()) {
            Bin foundedbin = optionalBin.get();
            foundedbin.setLocation(bin.getLocation());
            foundedbin.setLatitude(bin.getLatitude());
            foundedbin.setLongitude(bin.getLongitude());
            foundedbin.setWasteAmount(bin.getWasteAmount());
            foundedbin.setStatus(bin.getStatus());
            foundedbin.setFillLevel(bin.getFillLevel());
            return  binRepository.save(foundedbin);
        }
        throw  new RuntimeException("User not found");
    }

    private static final int MAX_FILL_LEVEL = 100;
    private static final int MIN_FILL_LEVEL = 0;
    private static final int INCREMENT_STEP = 10; // Change this value as needed
    private int currentBaseFillLevel = 0; // Base fill level that increases over time

    public Bin updateBinStatus(Long id, int fillLevel) {
        Bin bin = getBinById(id);
        if (bin != null) {
            bin.setFillLevel(fillLevel);
            if (fillLevel == MIN_FILL_LEVEL) {
                bin.setStatus(BinStatus.EMPTY);
            } else if (fillLevel < 50) {
                bin.setStatus(BinStatus.HALF_FULL);
            } else if (fillLevel < 75) {
                bin.setStatus(BinStatus.QUARTERLY_FULL);
            } else if (fillLevel < MAX_FILL_LEVEL) {
                bin.setStatus(BinStatus.FULL);
            } else {
                bin.setStatus(BinStatus.OVERFLOWING);
            }
            return binRepository.save(bin);
        }
        return null;
    }

//    @Scheduled(cron = "0/55 * * * * *")
    public void simulateSensorUpdates() {
        List<Bin> bins = getAllBins();
        bins.forEach(bin -> {
            // Ensure fill level is within the range
            int fillLevel = Math.min(currentBaseFillLevel + random.nextInt(INCREMENT_STEP + 1), MAX_FILL_LEVEL);
            updateBinStatus(bin.getId(), fillLevel);
        });

        // Increment base fill level to simulate increasing fill over time
        currentBaseFillLevel = Math.min(currentBaseFillLevel + INCREMENT_STEP, MAX_FILL_LEVEL);
    }

    // Find bins with full or overflow status and send them as jobs
//    @Scheduled(cron = "0 * * * * *")
    public void findAndAssignJobs() {
        List<Bin> fullAndOverflowingBins = getFullAndOverflowingBins();
        if (!fullAndOverflowingBins.isEmpty()) {
            routeFeignClient.assignJobs(fullAndOverflowingBins); // Notify the Route Planner Service
        }
    }
}


