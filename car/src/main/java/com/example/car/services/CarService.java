package com.example.car.services;

import com.example.car.entities.Car;
import com.example.car.models.CarResponse;
import com.example.car.models.Client;
import com.example.car.models.ClientDTO;
import com.example.car.repositories.CarRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CarService {
    private final CarRepository carRepository;
    private final RestTemplate restTemplate;

    private static final String CLIENT_API_BASE_URL = "http://localhost:8888/SERVICE-CLIENT/api/client/";

    public List<CarResponse> retrieveAllCars() {
        List<Car> carsList = carRepository.findAll();
        return carsList.stream()
                .map(this::buildCarResponseWithClientData)
                .collect(Collectors.toList());
    }

    public CarResponse retrieveCarById(Long carId) throws Exception {
        Car car = findCarOrThrow(carId);
        return buildCarResponseWithClientData(car);
    }

    private Car findCarOrThrow(Long carId) throws Exception {
        return carRepository.findById(carId)
                .orElseThrow(() -> new Exception(
                        String.format("Aucune voiture trouvée pour l'identifiant: %d", carId)));
    }

    private CarResponse buildCarResponseWithClientData(Car car) {
        Client clientData = fetchClientFromRemoteService(car.getClient_id());
        
        return CarResponse.builder()
                .id(car.getId())
                .brand(car.getBrand())
                .model(car.getModel())
                .matricule(car.getMatricule())
                .client(clientData)
                .build();
    }

    private Client fetchClientFromRemoteService(Long clientId) {
        if (clientId == null) {
            log.warn("L'ID du client est null, impossible de récupérer les données");
            return null;
        }

        try {
            String clientServiceUrl = CLIENT_API_BASE_URL + clientId;
            ClientDTO clientDTO = restTemplate.getForObject(clientServiceUrl, ClientDTO.class);
            
            return convertClientDtoToClient(clientDTO);
        } catch (RestClientException exception) {
            log.error("Erreur lors de la communication avec le service client pour l'ID {}: {}", 
                    clientId, exception.getMessage());
            return null;
        }
    }

    private Client convertClientDtoToClient(ClientDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Integer ageAsInteger = (dto.getAge() != null) ? dto.getAge().intValue() : null;
        return new Client(dto.getId(), dto.getNom(), ageAsInteger);
    }
}

