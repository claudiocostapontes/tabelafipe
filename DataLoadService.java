@Service
@RequiredArgsConstructor
public class DataLoadService {

    private final FipeClient fipeClient;
    private final BrandProducer brandProducer;

    public void startInitialLoad() {
        // Lógica para buscar as marcas no serviço da FIPE
        List<FipeBrandDTO> brands = fipeClient.getBrands();

        // Envia cada marca para a fila
        brands.forEach(brand -> brandProducer.sendBrandToQueue(brand.getNome()));
    }
}