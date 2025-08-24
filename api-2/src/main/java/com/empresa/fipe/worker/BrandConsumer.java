package com.empresa.fipe.worker;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BrandConsumer {

    private final VehicleProcessingService processingService;

    @RabbitListener(queues = "${queue.fipe.brand.name}") // Nome da fila vindo do application.properties
    public void receiveBrand(String brandName) {
        System.out.println("Recebida a marca para processamento: " + brandName);
        String valueOf = new String();
        processingService.processAndSaveVehiclesForBrand(brandName, valueOf);
    }
}