package com.example.car.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO pour recevoir les données du service Client
 * Correspond exactement à l'entité Client du service Client (nom, age Float)
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientDTO {
    private Long id;
    private String nom;  // Correspond au champ "nom" du service Client
    private Float age;   // Correspond au champ "age" Float du service Client
}

