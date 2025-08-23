// Em VehicleController.java (API-1)
@GetMapping("/brands")
@Operation(summary = "Busca todas as marcas de veículos distintas no banco de dados")
public ResponseEntity<List<String>> getBrands() {
    return ResponseEntity.ok(vehicleService.findAllBrands());
}

// Em VehicleService.java (API-1)
@Cacheable(value = "brands") // Cacheia o resultado desta consulta
public List<String> findAllBrands() {
    return vehicleRepository.findDistinctBrands();
}

// Em VehicleRepository.java (API-1)
@Query("SELECT DISTINCT v.brand FROM Vehicle v ORDER BY v.brand")
List<String> findDistinctBrands();