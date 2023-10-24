package com.projeto.pi.projeto_pi.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
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

import com.projeto.pi.projeto_pi.modals.users.User;
import com.projeto.pi.projeto_pi.modals.users.UserRepo;
import com.projeto.pi.projeto_pi.modals.users.UserRequestDTO;
import com.projeto.pi.projeto_pi.modals.users.UserResponseDTO;
import com.projeto.pi.projeto_pi.utils.ReplaceObjectAttributes;
import com.projeto.pi.projeto_pi.utils.ReponseBuilder;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

    @Autowired
    private UserRepo repository;

    @Autowired
    PasswordEncoder encoder;

    ReponseBuilder er = new ReponseBuilder();

    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        Optional<User> existingItemOptional = repository.findById(id);

        if (existingItemOptional.isPresent()) {
            return new ResponseEntity<UserResponseDTO>(existingItemOptional.get().toDTO(), HttpStatus.OK);
        } else {

            return er.error("Usuário não encontrado", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    @RolesAllowed("ADMIN")
    public Page<User> getAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        PageRequest of = PageRequest.of(page, size);

        Page<User> findAll = repository.findAll(of);
        return findAll;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid UserRequestDTO item) {
        try {
            User itemToBeSaved = item.toEntity();
            boolean exists = repository.findByLoginIgnoreCase(itemToBeSaved.getLogin()).isEmpty();
            if (exists) {
                return er.error("Usuário já existente", HttpStatus.CONFLICT);
            }
            itemToBeSaved.setSenha(encoder.encode(itemToBeSaved.getSenha()));
            User savedItem = repository.save(item.toEntity());
            return new ResponseEntity<>(savedItem, HttpStatus.CREATED);
        } catch (Exception e) {

            return er.error("Erro ao criar usuário", HttpStatus.EXPECTATION_FAILED);
        }
    }

    @DeleteMapping("{id}")
    @RolesAllowed("ADMIN")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        Optional<User> existingItemOptional = repository.findById(id);

        if (existingItemOptional.isPresent()) {
            repository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return er.error("Usuário não encontrado", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody UserRequestDTO item) {
        Optional<User> existingItemOptional = repository.findById(id);

        if (existingItemOptional.isPresent()) {
            User existingItem = existingItemOptional.get();

            ReplaceObjectAttributes<User> rep = new ReplaceObjectAttributes<>(existingItem);
            rep.replaceWith(item.toEntity());

            UserResponseDTO savedItem = repository.save(existingItem).toDTO();

            return new ResponseEntity<>(savedItem, HttpStatus.OK);
        } else {
            return er.error("Usuario não encontrado", HttpStatus.NOT_FOUND);
        }
    }
}
