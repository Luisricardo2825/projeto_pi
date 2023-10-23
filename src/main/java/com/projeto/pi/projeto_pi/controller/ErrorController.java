package com.projeto.pi.projeto_pi.controller;

import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping(value = "/")
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {
    private static final String PATH = "/error";

    @GetMapping(value = PATH)
    public ResponseEntity<Map<String, String>> error() {
        Map<String, String> existingItemOptional = null;
        existingItemOptional = Map.of("message", "Rota não encontrada");

        return new ResponseEntity<>(existingItemOptional, HttpStatus.NOT_FOUND);
    }

}
