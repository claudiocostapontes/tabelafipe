@Component
@RequiredArgsConstructor
public class BrandProducer {

    private final RabbitTemplate rabbitTemplate;
    private final Queue brandQueue; // Bean configurado com o nome da fila

    public void sendBrandToQueue(String brandName) {
        rabbitTemplate.convertAndSend(brandQueue.getName(), brandName);
    }
}