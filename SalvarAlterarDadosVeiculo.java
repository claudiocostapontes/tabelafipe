// Em VehicleController.java (API-1)
@PutMapping("/vehicles/{fipeCode}")
@Operation(summary = "Atualiza o modelo e observações de um veículo")
public ResponseEntity<VehicleDTO> updateVehicle(
    @PathVariable String fipeCode, @RequestBody @Valid UpdateVehicleDTO dto) {
    return ResponseEntity.ok(vehicleService.updateVehicle(fipeCode, dto));
}

// Em VehicleService.java (API-1)
@Transactional
@CacheEvict(value = "vehiclesByBrand", key = "#result.brand") // Invalida o cache para essa marca
public VehicleDTO updateVehicle(String fipeCode, UpdateVehicleDTO dto) {
    Vehicle vehicle = vehicleRepository.findByFipeCode(fipeCode)
        .orElseThrow(() -> new ResourceNotFoundException("Veículo não encontrado"));

    vehicle.setModel(dto.getModel());
    vehicle.setObservations(dto.getObservations());
    Vehicle updatedVehicle = vehicleRepository.save(vehicle);

    return convertToDto(updatedVehicle);
}