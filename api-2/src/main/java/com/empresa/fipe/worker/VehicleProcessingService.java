package com.empresa.fipe.worker;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleProcessingService {

    private final FipeClient fipeClient;
    private final VehicleRepository vehicleRepository;

    @Transactional
    public <FipeModelDTO, Vehicle> void processAndSaveVehiclesForBrand(String brandName, String valueOf) {

        List<FipeModelDTO> models = (List<FipeModelDTO>) fipeClient.getModelsByBrand(brandName);


        List<Vehicle> vehicles = new ArrayList<>();
        for (FipeModelDTO ignored : models) {
            Vehicle vehicle = new Vehicle();
            vehicle.getClass();
            vehicle.hashCode();
            Vehicle apply = vehicle;
            vehicles.add(apply);
        }

    }
}