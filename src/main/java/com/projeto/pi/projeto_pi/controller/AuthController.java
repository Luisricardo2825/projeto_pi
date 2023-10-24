package com.projeto.pi.projeto_pi.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.projeto.pi.projeto_pi.modals.auth.LoginRequestDTO;
import com.projeto.pi.projeto_pi.modals.users.User;
import com.projeto.pi.projeto_pi.services.TokenService;
import com.projeto.pi.projeto_pi.utils.ReponseBuilder;

import jakarta.validation.Valid;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
public class AuthController {

    ReponseBuilder res = new ReponseBuilder();
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping(value = "/login")
    public ResponseEntity<?> postMethodName(@RequestBody @Valid LoginRequestDTO entity) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(entity.getLogin(),
                entity.getSenha());
        token = new UsernamePasswordAuthenticationToken(entity.getLogin(), entity.getSenha());
        Authentication authenticate = authenticationManager.authenticate(token);

        User user = (User) authenticate.getPrincipal();
        String jwtToken = tokenService.generateToken(user);

        java.util.Map<String, Object> success = new HashMap<>();

        success.put("token", jwtToken);
        success.put("exp", tokenService.getExpirationDateFromToken());
        success.put("role", user.getRole());
        success.put("user", user.getId());
        success.put("name", user.getNome());
        success.put("login", user.getLogin());
        
        return new ResponseEntity<>(success,HttpStatus.ACCEPTED);
    }

    // ...
    @ExceptionHandler({ BadCredentialsException.class })
    public ResponseEntity<?> handleException(Exception ex, WebRequest request) {
        return res.error(ex.getMessage(), HttpStatus.FORBIDDEN);

    }

}
