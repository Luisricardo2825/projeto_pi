package com.projeto.pi.projeto_pi.configuration;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.projeto.pi.projeto_pi.modals.users.User;
import com.projeto.pi.projeto_pi.modals.users.UserRepo;
import com.projeto.pi.projeto_pi.services.TokenService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class MiddlewareToken extends OncePerRequestFilter {

    @Autowired
    TokenService tokenService;

    @Autowired
    UserRepo userRepo;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token;

        String authorization = request.getHeader("Authorization");
        if (authorization != null && authorization.startsWith("Bearer ")) {
            token = authorization.substring(7); // Remove "Bearer "
            String subject = this.tokenService.getLogin(token);

            if (subject != null) {
                User user = this.userRepo.findByLoginIgnoreCase(subject).get();
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                        user, null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        filterChain.doFilter(request, response); // Libera para o proximo Middleware/Filtro

    }

}
