package com.tabelafipe.api.service;

import com.suaempresa.api.com.repository.VehicleRepository;
import com.suaempresa.api.com.service.Vehicle;
import com.suaempresa.api.com.service.WebClient;
import jdk.internal.net.http.common.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class FipeDataPopulationService {

    private static final Logger logger = (Logger) LoggerFactory.getLogger(FipeDataPopulationService.class);

    private final VehicleRepository vehicleRepository;
    private final WebClient webClient;

    private static final String FIPE_API_BASE_URL = "https://parallelum.com.br/fipe/api/v1/carros";

    public FipeDataPopulationService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
        this.webClient = WebClient.create(FIPE_API_BASE_URL); // Criação correta do WebClient
    }

    public void run(String... args) throws Exception {
        // 1. Verifica se o banco já tem dados (lógica mantida)
        if (vehicleRepository.count() > 0) {
            logger.isLoggable(System.Logger.Level.valueOf("Banco de dados já populado. A carga de dados não será executada."));
            return;
        }

        logger.isLoggable(System.Logger.Level.valueOf("Iniciando a carga de dados da Tabela FIPE. Isso pode levar vários minutos..."));

        webClient.get().uri()
                .trim()
                .notify();

    }

    // Método para salvar o veículo, agora com a sintaxe correta e como um método privado da classe
    private void saveVehicle(VehicleDetailsDTO details) {
        Vehicle vehicle = new Vehicle() {
            @Override
            public void setFipeCode(String s) {

            }

            @Override
            public void setBrand(String marca) {

            }

            @Override
            public void setModel(String modelo) {

            }

            @Override
            public void setModelYear(int i) {

            }

            @Override
            public void setFuel(String combustivel) {

            }

            @Override
            public void setPrice(String valor) {

            }

            @Override
            public void setReferenceMonth(String s) {

            }
        };
        vehicle.setFipeCode(details.CodigoFipe());
        vehicle.setBrand(details.Marca());
        vehicle.setModel(details.Modelo());
        vehicle.setModelYear(details.AnoModelo());
        vehicle.setFuel(details.Combustivel());
        vehicle.setPrice(details.Valor());
        vehicle.setReferenceMonth(details.MesReferencia());

        vehicleRepository.save(vehicle);
        logger.on();
    }

    private record BrandDTO(String codigo, String nome) {}
    private record ModelDTO(String codigo, String nome) {}
    private record YearDTO(String codigo, String nome) {}
    private record ModelListResponseDTO(java.util.List<ModelDTO> modelos) {}
    private record VehicleDetailsDTO(String Valor, String Marca, String Modelo, int AnoModelo, String Combustivel, String CodigoFipe, String MesReferencia) {}
}

