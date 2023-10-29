package com.projeto.pi.projeto_pi.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.projeto.pi.projeto_pi.modals.auth.LoginRequestDTO;
import com.projeto.pi.projeto_pi.modals.auth.LoginResponseDTO;
import com.projeto.pi.projeto_pi.modals.users.User;
import com.projeto.pi.projeto_pi.services.TokenService;
import com.projeto.pi.projeto_pi.utils.ReponseBuilder;

import ch.qos.logback.classic.Logger;
import jakarta.validation.Valid;

import java.time.Instant;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthController {
    private static String ERROR_LOGIN = "Usuário inexistente ou senha inválida";
    // Logger
    private static Logger logger = (Logger) LoggerFactory.getLogger(AuthController.class);

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

        Instant myDate = tokenService.getExpirationDateFromToken();
        String role = user.getRole();
        Long userId = user.getId();
        String login = user.getLogin();
        String nome = user.getNome();

        LoginResponseDTO loginReponse = new LoginResponseDTO(jwtToken, myDate, role, userId, role, nome, login);

        return new ResponseEntity<>(loginReponse, HttpStatus.ACCEPTED);
    }

    // ...
    @ExceptionHandler({ BadCredentialsException.class })
    public ResponseEntity<?> handleException(Exception ex, WebRequest request) {

        logger.error(ERROR_LOGIN);
        logger.error(ex.getMessage());
        logger.error(ex.getStackTrace().toString());

        return res.error(ERROR_LOGIN, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(AuthenticationException.class) // exception handled
    public ResponseEntity<?> handleExceptions(
            AuthenticationException ex) {

        logger.error(ERROR_LOGIN);
        logger.error(ex.getMessage());
        logger.error(ex.getStackTrace().toString());

        return res.error(ERROR_LOGIN, HttpStatus.FORBIDDEN);
    }

}
