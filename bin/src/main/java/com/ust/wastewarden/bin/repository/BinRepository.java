package com.ust.wastewarden.bin.repository;

import com.ust.wastewarden.bin.model.Bin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BinRepository extends JpaRepository<Bin, Long> {

    // Find bins with both FULL and OVERFLOW statuses
    List<Bin> findByStatusIn(List<String> statuses);
}
