package com.projeto.pi.projeto_pi.services;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.projeto.pi.projeto_pi.modals.users.User;

@Service
public class TokenService {

    // Não é o mais seguro, mas serve pro proposito :/
    private String secret = "43cu95n8r34v8975yb834589tvm456049nt4y5o0";

    public String generateToken(User user) {
        return JWT.create()
                .withIssuer("/login")
                .withSubject(user.getUsername())
                .withClaim("id", user.getId())
                .withExpiresAt(
                        this.getExpirationDateFromToken() // UTC -03:00 Brasil
                ).sign(Algorithm.HMAC256(secret));
    }

    public String getLogin(String token) {
        return JWT.require(Algorithm.HMAC256(secret))
                .withIssuer("/login").build().verify(token).getSubject();
    }

    public Instant getExpirationDateFromToken() {
        return LocalDateTime.now()
                .plusMinutes(10)
                .toInstant(ZoneOffset.of("-03:00"));
    }

}
