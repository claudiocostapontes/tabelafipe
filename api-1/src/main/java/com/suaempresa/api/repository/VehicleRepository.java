package com.suaempresa.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VehicleRepository<Vehicle> extends JpaRepository<com.suaempresa.api.repository.Vehicle, Long> {

    Optional<Vehicle> findByFipeCode(String fipeCode);

}
