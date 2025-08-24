package com.suaempresa.api.gateway;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public record DataLoadService<FipeBrandDTO, BrandProducer>(FipeClient fipeClient, BrandProducer brandProducer) {

    public void startInitialLoad() {

        List<FipeBrandDTO> brands = fipeClient.getBrands();
        brands.forEach(this::accept);
    }

    private void accept(FipeBrandDTO brand) {

    }
}

