package com.projeto.pi.projeto_pi.services;

import java.time.Instant;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.projeto.pi.projeto_pi.modals.users.User;

@Service
public class TokenService {

    @Value("${jwt.token.exp:600}")
    private Long tokenExpiration;

    // Não é o mais seguro, mas serve pro proposito :/
    @Value("${jwt.token.secret:43cu95n8r34v8975yb834589tvm456049nt4y5o0}")
    private String secret;

    public String generateToken(User user) {
        return JWT.create()
                .withClaim("carros", true)
                .withClaim("users", user.getRole().equals("ROLE_ADMIN"))
                .withSubject(user.getUsername())
                .withClaim("id", user.getId())
                .withExpiresAt(
                        this.getExpirationDateFromToken() // UTC -03:00 Brasil
                ).sign(Algorithm.HMAC256(secret));
    }

    public String verify(String token) {
        return JWT.require(Algorithm.HMAC256(secret)).build().verify(token).getSubject();
    }

    public Instant getExpirationDateFromToken() {

        return new Date(System.currentTimeMillis())
                .toInstant().plusSeconds(tokenExpiration);

    }

}
