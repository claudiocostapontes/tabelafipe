// Em VehicleController.java (API-1)
@GetMapping("/brands/{brandName}/vehicles")
@Operation(summary = "Busca veículos por nome da marca")
public ResponseEntity<List<VehicleDTO>> getVehiclesByBrand(@PathVariable String brandName) {
    return ResponseEntity.ok(vehicleService.findByBrand(brandName));
}

// Em VehicleService.java (API-1)
@Cacheable(value = "vehiclesByBrand", key = "#brandName") // Chave do cache é o nome da marca
public List<VehicleDTO> findByBrand(String brandName) {
    // Lógica para buscar no repositório e mapear para DTO
    return vehicleRepository.findByBrand(brandName).stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
}