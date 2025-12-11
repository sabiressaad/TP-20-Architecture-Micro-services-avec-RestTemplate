package com.example.car.controllers;

import com.example.car.models.CarResponse;
import com.example.car.services.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/car")
@RequiredArgsConstructor
public class CarController {
    private final CarService carService;

    @GetMapping
    public ResponseEntity<List<CarResponse>> getAllCars() {
        List<CarResponse> carsList = carService.retrieveAllCars();
        return ResponseEntity.ok().body(carsList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCarById(@PathVariable("id") Long carId) {
        try {
            CarResponse carResponse = carService.retrieveCarById(carId);
            return ResponseEntity.ok().body(carResponse);
        } catch (Exception exception) {
            return createErrorResponse(exception.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    private ResponseEntity<Map<String, Object>> createErrorResponse(String errorMessage, HttpStatus status) {
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("message", errorMessage);
        errorDetails.put("statusCode", status.value());
        errorDetails.put("timestamp", System.currentTimeMillis());
        return ResponseEntity.status(status).body(errorDetails);
    }
}

