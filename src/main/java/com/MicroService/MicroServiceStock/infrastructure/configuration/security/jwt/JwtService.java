package com.MicroService.MicroServiceStock.infrastructure.configuration.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Function;


@Service
public class JwtService {

    private final String SECRET_KEY = "294A404E635266556A586E327235753878214125442A472D4B6150645367566B";
    private SecretKey getSignInKey() {
        byte[] keyBytes = Base64.getDecoder().decode(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
        return new SecretKeySpec(keyBytes, SignatureAlgorithm.HS256.getJcaName());
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }


    public boolean isTokenValid(String token) {
        // Lógica de validación del token
        return !isTokenExpired(token);
    }

    public Collection<? extends GrantedAuthority> extractAuthorities(Claims claims) {
        var roles = (List<Map<String, String>>) claims.get("role");
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.get("authority")))
                .toList();
    }


    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

    }

    // Validación de la expiración del token
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
}

