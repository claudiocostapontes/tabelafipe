package com.suaempresa.api.gateway;

import org.springframework.stereotype.Service;

import java.util.List;

public record DataLoadService<FipeBrandDTO, BrandProducer>(
        com.suaempresa.api.gateway.FipeClient fipeClient, BrandProducer brandProducer) {

    public void startInitialLoad() {

        List<FipeBrandDTO> brands = fipeClient.getBrands();
        brands.forEach(this::accept);
    }

    private void accept(FipeBrandDTO brand) {

    }
}

