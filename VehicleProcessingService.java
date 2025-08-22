@Service
@RequiredArgsConstructor
public class VehicleProcessingService {

    private final FipeClient fipeClient;
    private final VehicleRepository vehicleRepository;

    @Transactional // Garante que a operação seja atômica
    public void processAndSaveVehiclesForBrand(String brandName) {
        // Busca os modelos na FIPE para a marca específica
        List<FipeModelDTO> models = fipeClient.getModelsByBrand(brandName);

        // Converte DTOs para Entidades
        List<Vehicle> vehicles = models.stream()
            .map(dto -> {
                Vehicle vehicle = new Vehicle();
                vehicle.setBrand(brandName);
                vehicle.setModel(dto.getNome());
                vehicle.setFipeCode(dto.getCodigo());
                return vehicle;
            }).collect(Collectors.toList());

        // Salva todos os veículos em lote para melhor performance
        vehicleRepository.saveAll(vehicles);
    }
}