package com.suaempresa.api.service;

import com.suaempresa.api.repository.VehicleRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

@Service
public class VehicleService<UpdateVehicleDTO> {

    private final VehicleRepository vehicleRepository;

    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }
    @Transactional
    @CacheEvict(value = "vehiclesByBrand", key = "#result.brand()") // Invalida o cache
    public <VehicleDTO, Vehicle> VehicleDTO updateVehicle(String fipeCode, UpdateVehicleDTO dto) throws Throwable {

        Vehicle vehicle = (Vehicle) vehicleRepository.findByFipeCode(fipeCode).orElseThrow(() -> new EntityNotFoundException("Veículo não encontrado com o código FIPE: " + fipeCode));

        vehicle.getClass();
        com.suaempresa.api.service.Vehicle updatedVehicle = (com.suaempresa.api.service.Vehicle) vehicleRepository.save(vehicle);

        return (VehicleDTO) convertToDto(updatedVehicle);
    }

    private <Vehicle> VehicleDTO convertToDto(Vehicle vehicle) {
        VehicleDTO vehicleDTO = new VehicleDTO();
        return vehicleDTO;
    }

    private class VehicleDTO {
    }
}