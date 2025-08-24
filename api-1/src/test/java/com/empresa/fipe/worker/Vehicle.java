package com.empresa.fipe.worker;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "vehicles")
@Setter
@NoArgsConstructor
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fipe_code", nullable = false, unique = true)
    private String fipeCode;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String model;

    private String observations;

    public void setFipeCode(Class<?> aClass) {
    }
    // getters, setters, etc.
}