package com.uniSystemAPI.Infra;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.uniSystemAPI.domain.usuario.Usuario;

@Service
public class TokenService {

    @Value("${uni.api.security.token.secret}") // ver application.properties
    private String secret;

    public String generateToken(Usuario usuario) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create().withIssuer("uniSystemApi")
                    .withSubject(usuario.getLogin())
                    .withExpiresAt(getExpirationDate())
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException ex) {
            throw new RuntimeException("Error while generating token ", ex);
        }
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("uniSystemApi")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTCreationException ex) {
            return "";
        }
    }

    private Instant getExpirationDate() {
        return LocalDateTime.now().plusDays(10).toInstant(ZoneOffset.of("-03:00"));
        // return LocalDateTime.now().plusHours(12).toInstant(ZoneOffset.of("-03:00"));
    }

    public String getUserSubjectFromToken(String token) {
        // Obtém as reivindicações do token
        try {
          //  Algorithm algorithm = Algorithm.HMAC256(secret);
            var claims = JWT.decode(token);

            // Obtém o ID do usuário
            return claims.getSubject();
        } catch (JWTCreationException e) {
            // TODO: handle exception
        }

        return "Não foi possivel Localizar o usuário através do token informado";
    }
}
