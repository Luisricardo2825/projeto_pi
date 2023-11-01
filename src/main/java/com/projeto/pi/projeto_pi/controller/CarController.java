package com.projeto.pi.projeto_pi.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.projeto.pi.projeto_pi.modals.cars.Car;
import com.projeto.pi.projeto_pi.modals.cars.CarRepo;
import com.projeto.pi.projeto_pi.modals.cars.CarRequestDTO;
import com.projeto.pi.projeto_pi.modals.cars.CarResponseDTO;
import com.projeto.pi.projeto_pi.utils.ReponseBuilder;
import com.projeto.pi.projeto_pi.utils.ReplaceObjectAttributes;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/cars")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CarController {

    @Autowired
    private CarRepo repository;

    ReponseBuilder er = new ReponseBuilder();

    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String name = auth.getName();

        try {
            if (name.equalsIgnoreCase("admin")) {
                Optional<Car> existingItemOptional = repository.findById(id);
                if (existingItemOptional.isPresent()) {
                    return new ResponseEntity<CarResponseDTO>(existingItemOptional.get().toDTO(), HttpStatus.OK);
                }
            } else {
                Optional<Car> existingItemOptional = repository.findByIdActive(id);

                if (existingItemOptional.isPresent()) {
                    return new ResponseEntity<CarResponseDTO>(existingItemOptional.get().toDTO(), HttpStatus.OK);
                }
            }
            throw new Exception("Carro não encontrado");
        } catch (Exception e) {
            return er.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping
    public Page<CarResponseDTO> getAll(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "true") Boolean all,
            @RequestParam(defaultValue = "") String modelo, @RequestParam(required = false) List<String> marca) {
        PageRequest of = PageRequest.of(page, size);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String name = auth.getName();
        if (marca == null) {
            marca = repository.findAllMarcas();
        }
        if (name.equalsIgnoreCase("admin")) {

            List<CarResponseDTO> findAll = repository.searchBy(modelo, marca, all).stream()
                    .map(car -> car.toDTO()).toList();
            int start = (int) of.getOffset();
            int end = Math.min((start + of.getPageSize()), findAll.size());

            List<CarResponseDTO> pageContent = findAll.subList(start, end);

            return new PageImpl<>(pageContent, of, findAll.size());
        }

        List<CarResponseDTO> findAll = repository.searchBy(modelo, marca, false).stream()
                .map(car -> car.toDTO()).toList();
        int start = (int) of.getOffset();
        int end = Math.min((start + of.getPageSize()), findAll.size());

        List<CarResponseDTO> pageContent = findAll.subList(start, end);

        return new PageImpl<>(pageContent, of, findAll.size());
    }

    @GetMapping
    @RequestMapping("/marcas")
    public List<String> getMarcas() {
        return repository.findAllMarcas();
    }

    @PostMapping
    @RolesAllowed({ "ADMIN" })
    public ResponseEntity<?> create(@RequestBody @Valid CarRequestDTO item) {
        try {
            Car savedItem = repository.save(item.toEntity());
            return new ResponseEntity<>(savedItem, HttpStatus.CREATED);
        } catch (Exception e) {
            return er.error("Erro ao criar carro", HttpStatus.EXPECTATION_FAILED);
        }
    }

    @DeleteMapping("{id}")
    @RolesAllowed({ "ADMIN" })
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        Optional<Car> existingItemOptional = repository.findById(id);

        if (existingItemOptional.isPresent()) {
            repository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return er.error("Carro não encontrado", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("{id}")
    @RolesAllowed({ "ADMIN" })
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody CarRequestDTO item) {
        Optional<Car> existingItemOptional = repository.findById(id);

        if (existingItemOptional.isPresent()) {
            Car existingItem = existingItemOptional.get();

            // Realiza a atualização dos dados
            ReplaceObjectAttributes<Car> rep = new ReplaceObjectAttributes<>(existingItem);
            rep.replaceWith(item.toEntity());
            Car savedItem = repository.save(existingItem);

            return new ResponseEntity<>(savedItem, HttpStatus.OK);
        } else {

            return er.error("Carro não encontrado", HttpStatus.NOT_FOUND);
        }
    }

    // @ExceptionHandler({ Exception.class })
    // public ResponseEntity<?> handleException(Exception ex, WebRequest request) {
    // return er.error(ex.getMessage(), HttpStatus.FORBIDDEN);
    // }
    @ExceptionHandler({ AccessDeniedException.class })
    public ResponseEntity<?> handleAccessException(Exception ex, WebRequest request) {
        return er.error("Acesso negado. Nivel de permissão insuficiente", HttpStatus.FORBIDDEN);
    }
}
