package com.ust.wastewarden.truck.repository;

import com.ust.wastewarden.truck.model.Route;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RouteRepository extends JpaRepository<Route, Long> {
}