package com.suaempresa.api.DTO;

/**
 * DTO para transferir dados de veículos de forma imutável e concisa.
 */
public record VehicleDTO(String model, String brand, int year) {
}