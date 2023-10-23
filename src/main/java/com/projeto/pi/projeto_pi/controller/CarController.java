package com.projeto.pi.projeto_pi.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.pi.projeto_pi.cars.Car;
import com.projeto.pi.projeto_pi.cars.CarRepo;
import com.projeto.pi.projeto_pi.cars.CarRequestDTO;
import com.projeto.pi.projeto_pi.cars.CarResponseDTO;
import com.projeto.pi.projeto_pi.utils.ReplaceObjectAttributes;

import jakarta.validation.Valid;

import java.util.HashMap;

@RestController
@RequestMapping("/cars")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CarController {

    @Autowired
    private CarRepo repository;

    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        Optional<Car> existingItemOptional = repository.findById(id);

        if (existingItemOptional.isPresent()) {
            return new ResponseEntity<CarResponseDTO>(existingItemOptional.get().toDTO(), HttpStatus.OK);
        } else {
            java.util.Map<String, String> error = new HashMap<>();
            error.put("message", "Carro não encontrado");
            error.put("code", "404");
            error.put("status", "Not Found");
            error.put("timestamp", String.valueOf(System.currentTimeMillis()));
            error.put("path", "/cars/" + id);
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public Page<Car> getAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        PageRequest of = PageRequest.of(page, size);

        Page<Car> findAll = repository.findAll(of);
        return findAll;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid CarRequestDTO item) {
        try {
            Car savedItem = repository.save(item.toEntity());
            return new ResponseEntity<>(savedItem, HttpStatus.CREATED);
        } catch (Exception e) {
            java.util.Map<String, String> error = new HashMap<>();
            error.put("message", "Erro ao criar carro");
            error.put("code", "500");
            error.put("status", "Internal Server Error");
            error.put("timestamp", String.valueOf(System.currentTimeMillis()));
            error.put("path", "/cars");
            return new ResponseEntity<>(error, HttpStatus.EXPECTATION_FAILED);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        Optional<Car> existingItemOptional = repository.findById(id);

        if (existingItemOptional.isPresent()) {
            repository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            java.util.Map<String, String> error = new HashMap<>();
            error.put("message", "Carro não encontrado");
            error.put("code", "404");
            error.put("status", "Not Found");
            error.put("timestamp", String.valueOf(System.currentTimeMillis()));
            error.put("path", "/cars/" + id);
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody CarRequestDTO item) {
        Optional<Car> existingItemOptional = repository.findById(id);

        java.util.Map<String, String> error = new HashMap<>();
        error.put("message", "Carro não encontrado");
        error.put("code", "404");
        error.put("status", "Not Found");
        error.put("timestamp", String.valueOf(System.currentTimeMillis()));
        error.put("path", "/cars/" + id);

        if (existingItemOptional.isPresent()) {
            Car existingItem = existingItemOptional.get();

            ReplaceObjectAttributes<Car> rep = new ReplaceObjectAttributes<>(existingItem);
            rep.replaceWith(item.toEntity());

            Car savedItem = repository.save(existingItem);

            return new ResponseEntity<>(savedItem, HttpStatus.OK);
        } else {

            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
    }
}
