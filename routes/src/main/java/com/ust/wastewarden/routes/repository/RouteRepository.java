package com.ust.wastewarden.routes.repository;


import com.ust.wastewarden.routes.model.Route;
import com.ust.wastewarden.routes.model.RouteStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {
    List<Route> findByStatus(RouteStatus status);
}
