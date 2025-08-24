package com.suaempresa.api.controller;

import com.suaempresa.api.service.VehicleService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PutMapping("/vehicles/{fipeCode}")
    @Operation(summary = "Atualiza o modelo e observações de um veículo")
    public ResponseEntity<com.suaempresa.api.controller.VehicleDTO> updateVehicle(
            @PathVariable String fipeCode,
            @RequestBody @Valid com.suaempresa.api.controller.UpdateVehicleDTO dto) throws Throwable {

        com.suaempresa.api.controller.VehicleDTO updatedVehicle = (com.suaempresa.api.controller.VehicleDTO) vehicleService.updateVehicle(fipeCode, dto);
        return ResponseEntity.ok(updatedVehicle);
    }
}