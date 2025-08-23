@Component
@RequiredArgsConstructor
public class BrandConsumer {

    private final VehicleProcessingService processingService;

    public BrandConsumer(VehicleProcessingService processingService) {
        this.processingService = processingService;
    }

    @RabbitListener(queues = "${queue.fipe.brand.name}") // Nome da fila vindo do application.properties
    public void receiveBrand(String brandName) {
        System.out.println("Recebida a marca para processamento: " + brandName);
        processingService.processAndSaveVehiclesForBrand(brandName);
    }
}