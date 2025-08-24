package com.suaempresa.api.controller;

import com.suaempresa.api.com.controller.UpdateVehicleDTO;
import com.suaempresa.api.com.controller.VehicleDTO;
import com.suaempresa.api.com.service.VehicleService;
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
    public ResponseEntity<VehicleDTO> updateVehicle(
            @PathVariable String fipeCode,
            @RequestBody @Valid UpdateVehicleDTO dto) throws Throwable {

        VehicleDTO updatedVehicle = (VehicleDTO) vehicleService.updateVehicle(fipeCode, dto);
        return ResponseEntity.ok(updatedVehicle);
    }
}