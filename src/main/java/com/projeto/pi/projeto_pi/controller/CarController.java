package com.projeto.pi.projeto_pi.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.pi.projeto_pi.cars.Car;
import com.projeto.pi.projeto_pi.cars.CarRepo;
import com.projeto.pi.projeto_pi.cars.CarResponseDTO;

@RestController
@RequestMapping("cars")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CarController {

    @Autowired
    private static CarRepo repository;

    @GetMapping("{id}")
    public ResponseEntity<CarResponseDTO> getById(@PathVariable("id") Long id) {
        Optional<Car> existingItemOptional = repository.findById(id);

        if (existingItemOptional.isPresent()) {
            return new ResponseEntity<>(existingItemOptional.get().toDTO(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public List<CarResponseDTO> getAll() {
        // use jackson to convert to json

        List<Car> all = repository.findAll();
        List<CarResponseDTO> allDTO = new ArrayList<>();
        for (Car car : all) {
            allDTO.add(car.toDTO());
        }
        return allDTO;

    }

}
