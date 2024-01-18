package com.gm2.pdv.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;  // Importa a classe Keys para obter uma SecretKey
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Calendar;
import java.util.Date;

@Service  // Movido a anotação @Service para a classe
public class JwtService {

    @Value("${security.jwt.expiration}")
    private int expiration;

    @Value("${security.jwt.key}")
    private String key;  // Corrigido o tipo da variável para String

    public String generateToken(String username) {  // Corrigido o nome do método para generateToken
        Calendar currentTimeNow = Calendar.getInstance();
        currentTimeNow.add(Calendar.MINUTE, expiration);
        Date expirationDate = currentTimeNow.getTime();

        SecretKey secretKey = getSecretKey();

        return Jwts.builder()
                .setSubject(username)
                .setExpiration(expirationDate)
                .signWith(secretKey)
                .compact();
    }

    private SecretKey getSecretKey() {
        // Aqui você pode implementar a lógica para obter a chave secreta.
        // Exemplo simples usando a chave em bytes (deve ser tratado de forma mais segura na prática):
        byte[] keyBytes = key.getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
